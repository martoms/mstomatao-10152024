<script setup lang="ts">
  import { toRefs, ref } from 'vue';
  import type { UpdateDraggedItemFN, UpdateDropZoneFN, UpdateTasksFN, UpdateStateFN, HeaderOptions, State } from '../../types/types.js';
  import useFetch from '../../composables/useFetch.js';

  const props = defineProps<{
    id: string;
    title: string;
    state: State;
    description: string;
    updateDraggedItem: UpdateDraggedItemFN;
    updateDropZone: UpdateDropZoneFN;
    updateTasks: UpdateTasksFN;
    updateState: UpdateStateFN;
  }>()
  const { id, title, description, state } = toRefs(props);

  const isDragging = ref(false)
  const $draggable = ref()
  const showDesc = ref(false)

  const handleDragStart = () => {
    isDragging.value = true
    const itemId = id.value
    props.updateDraggedItem(itemId)
  }

  const handleDragend = () => {
    isDragging.value = false;
    props.updateDraggedItem('');
    props.updateDropZone('');
  }

  const handleDelete = () => {
    const options: HeaderOptions = {
      method: 'DELETE',
      headers: {'Content-Type': 'application/json'},
    }
    const API = import.meta.env.VITE_API_URL;
    useFetch(`${API}/${id.value}`, options, props.updateTasks, props.updateState);
  }

  const handleEdit = () => {
    state.value.addTask = false;
    state.value.showForm = true;
    state.value.activeItem = id.value;
  }

  const handleHoverBtn = (e: Event) => {
    const target = e.target as HTMLImageElement
    if (target) {
      if (target.alt === 'edit') {
        target.src = '/src/assets/edit-solid.svg'
      } else {
        target.src = '/src/assets/trash-solid.svg'
      }
    }
  }

  const handleLeaveBtn = (e: Event) => {
    const target = e.target as HTMLImageElement
    if (target) {
      if (target.alt === 'edit') {
        target.src = '/src/assets/edit.svg'
      } else {
        target.src = '/src/assets/trash.svg'
      }
    }
  }

</script>

<template>
  <div
    class="task"
    :class='{ dragging: isDragging }'
    :ref='(el) => { $draggable = el }'
    draggable=true
    @dragstart='handleDragStart'
    @dragend='handleDragend'
  >
    <div class="title">
      <p class="title" @click='showDesc = !showDesc'>
        {{ title }}
      </p>
      <span class="buttons">
        <img
          src="../../assets/edit.svg"
          alt="edit"
          @click='handleEdit'
          @mouseover='handleHoverBtn'
          @mouseleave='handleLeaveBtn'
         >
         <img
          src="../../assets/trash.svg"
          alt="delete"
          @click='handleDelete'
          @mouseover='handleHoverBtn'
          @mouseleave='handleLeaveBtn'
         >
      </span>
    </div>
    <div class="desc" v-show='showDesc'>
      {{ description }}
    </div>
  </div>
</template>

<style scoped>
  div.task {
    background-color: var(--white);
    padding: var(--space2);
    border-radius: 5px;
    margin-bottom: var(--space2);
    font-weight: 600;
    cursor: grab;
  }

  div.task:active {
    cursor: grabbing;
  }

  div.task.dragging {
    opacity: 0.5;
  }

  p.title {
    max-width: 85%;
    cursor: pointer;
  }

  div.title {
    display: flex;
    justify-content: space-between;
  }

  div.title img {
    height: 20px;
    width: auto;
    margin-left: var(--space2);
    cursor: pointer;
  }

  div.desc {
    margin-top: var(--space3);
    padding-top: var(--space3);
    border-top: 2px solid var(--blue);
    font-weight: 400;
  }
</style>