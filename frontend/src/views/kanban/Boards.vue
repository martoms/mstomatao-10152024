<script setup lang="ts">
  import type { Task, UpdateTasksFN, UpdateStateFN, HeaderOptions, State } from '../../types/types.js';
  import useFetch from '../../composables/useFetch.js';
  import Board from './Board.vue';
  import { toRefs, ref } from 'vue';

  const props = defineProps<{
    state: State;
    tasks: Task[];
    updateTasks: UpdateTasksFN;
    updateState: UpdateStateFN;
  }>()
  const { tasks } = toRefs(props)

  const draggedItem = ref('')
  const dropZone = ref('')

  const boards = [
      {id: 1, class: 'todo', header: 'To Do'},
      {id: 2, class: 'in-progress', header: 'In Progress'},
      {id: 3, class: 'done', header: 'Done'}
    ]

  function updateDraggedItem(item: string) {
    draggedItem.value = item
  }

  function updateDropZone(zone: string) {
    dropZone.value = zone
  }

  function updateTaskList() {
    const id = draggedItem.value
    const status = dropZone.value
    const task = tasks.value.find(t => t.id === id)
    task && (task.status = status)
    const body = JSON.stringify({ status })
    const options: HeaderOptions = {
      method: 'PATCH',
      headers: {'Content-Type': 'application/json'},
      body
    }
    const API = import.meta.env.VITE_API_URL;
    useFetch(`${API}/${id}`, options, props.updateTasks, props.updateState);
  }
</script>

<template>
  <div class="boards">
    <Board
      v-for='b in boards'
      :key='b.id'
      :c='b.class'
      :header='b.header'
      :state='state'
      :tasks='tasks'
      :updateDraggedItem='updateDraggedItem' 
      :updateDropZone='updateDropZone'  
      :updateTaskList='updateTaskList'
      :updateTasks='updateTasks'
      :updateState='updateState'
      :dropZone='dropZone'
    />
  </div>
</template>

<style scoped>
  div.boards {
    height: 70vh;
    display: flex;
    justify-content: space-evenly;
    gap: 5px;
  }
</style>