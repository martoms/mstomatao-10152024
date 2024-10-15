<script setup lang="ts">
  import Task from './Task.vue'
  import type { Task as TaskType, State, UpdateDraggedItemFN, UpdateDropZoneFN, UpdateTaskListFN, UpdateTasksFN, UpdateStateFN } from '../../types/types.js';
  import { computed, toRefs, ref } from 'vue';

  const props = defineProps<{
    state: State;
    tasks: TaskType[];
    c: string;
    header: string;
    updateDraggedItem: UpdateDraggedItemFN;
    updateDropZone: UpdateDropZoneFN;
    updateTaskList: UpdateTaskListFN;
    updateTasks: UpdateTasksFN;
    updateState: UpdateStateFN;
    dropZone: string;
  }>()
  const { tasks, c, header, dropZone } = toRefs(props)
 
  const getTasks = computed<TaskType[]>(() => {
    return tasks.value.filter(task => task.status === header.value.toUpperCase())
  })

  const handleDragover = () => {
    props.updateDropZone(header.value.toUpperCase());
    console.log(dropZone.value)
  }

  const handleDrop = () => {
    props.updateTaskList()
  }

  const isHovered = computed(() => {
    return header.value.toUpperCase() === dropZone.value
  })
  
</script>

<template>
  <div
    :class="c"
    class='container'
    @dragover.prevent='handleDragover'
    @drop.prevent='handleDrop'
  >
    <h2>{{ header }}</h2>
    <div class="tasks" :class='{ focus: isHovered }'>
      <Task
        v-for='task in getTasks'
        :key='task.id'
        :id='task.id'
        :title='task.name'
        :description='task.description'
        :state='state'
        :updateDraggedItem='updateDraggedItem'
        :updateDropZone='updateDropZone'
        :updateTasks='updateTasks'
        :updateState='updateState'
      />
    </div>
  </div>
</template>

<style scoped>
  div.container {
    width: 30%;
  }

  h2 {
    margin-top: 30px;
    text-align: center;
    color: var(--blue);
  }

  div.tasks {
    height: 80%;
    width: 100%;
    border: solid var(--white) 5px;
    border-radius: 10px;
    padding: var(--space2);
    overflow-y: auto;
  }

  div.tasks.focus {
    background-color: rgba(100, 100, 100, 0.2);
    filter: drop-shadow(0 0 5px var(--blue));
  }
</style>