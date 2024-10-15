<script setup lang="ts">
  import { toRefs, ref } from 'vue';

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

  
</script>

<template>
  <div class="boards">
    
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