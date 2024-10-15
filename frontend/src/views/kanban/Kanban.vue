<script setup lang="ts">
  import { onMounted, ref, reactive, watchEffect } from 'vue';
  import type { Task, State, Pending, StateError } from '../../types/types.js';
  import useFetch from '../../composables/useFetch.js';
  import TaskForm from './TaskForm.vue';
  import Boards from './Boards.vue';

  const tasks = ref<Task[]>([])
  const state = reactive<State>({
    showForm: false,
    addTask: true,
    activeItem: '',
    isPending: false,
    error: null
  })

  onMounted(() => {
    const API = import.meta.env.VITE_API_URL;
    useFetch(API, undefined, updateTasks, updateState);
  })

  function updateTasks(newTasks: Task[]) {
    tasks.value = newTasks
  }

  function updateState(pending: Pending = undefined, error: StateError = undefined) {
    pending && (state.isPending = pending);
    error && (state.error = error);
  }
</script>

<template>
  <header>
    <a href="https://www.linkedin.com/in/m-tomatao/" target="_blank">
      <img src="../../assets/marjohn-banner.svg" alt="marjohn">
    </a>
    <h1 id="kanban">Kanban</h1>
  </header>
  <main>
    <button id="createtask" class="taskform" @click='state.showForm = true'>
      <img src="../../assets/add-w.svg" alt="add.svg">
      Create Task
    </button>
    <TaskForm
      v-show='state.showForm'
      :state='state'
      :tasks='tasks'
      :addTask='state.addTask'
      :activeItem='state.activeItem'
      :updateTasks='updateTasks'
      :updateState='updateState'
    />
    <Boards
      :tasks='tasks'
      :state='state'
      :updateTasks='updateTasks'
      :updateState='updateState'
    />
  </main>
  <footer>
    <p>© All Rights Reserved 2024 | Marjohn Tomatao | Vauldex</p>
  </footer>
</template>

<style scoped>
  header {
    display: flex;
    flex-direction: column;
    background-color: var(--white);
    padding: var(--space3);
  }

  header a {
    display: block;
    margin: auto;
  }

  header img {
    height: 30px;
    width: auto;
  }

  header h1 {
    text-align: center;
    font-size: 3rem;
    font-weight: 800;
  }

  main {
    position: relative;
    background-color: var(--black);
  }

  button {
    position: relative;
    top: var(--space3);
    display: flex;
    margin: auto;
    gap: var(--space2);
    font-size: 1.2rem;
    font-weight: 600;
  }

  footer {
    background-color: var(--grey);
    padding: var(--space2);
    text-align: center;
    font-size: 0.8rem;
  }

  footer p {
    color: var(--white);
  }
</style>
