<script setup lang="ts">
  import type { Task } from '@/types/types';
  import { computed, toRefs, ref } from 'vue';

</script>

<template>
  <div
    class='container'
  >
    <div class="tasks">
      
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