<script setup lang="ts">
  import { toRefs, ref } from 'vue';

  const isDragging = ref(false)
  const $draggable = ref()
  const showDesc = ref(false)

</script>

<template>
  <div
    class="task"
  >
    <div class="title">
      <p class="title" >

      </p>
      <span class="buttons">
        <img
          src="../../assets/edit.svg"
          alt="edit"
          
         >
         <img
          src="../../assets/trash.svg"
          alt="delete"
          
         >
      </span>
    </div>
    <div class="desc">
      
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