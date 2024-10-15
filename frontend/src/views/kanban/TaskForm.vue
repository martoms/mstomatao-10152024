<script setup lang="ts">
  import { ref, toRefs, watch } from 'vue';
  import useFetch from '../../composables/useFetch.js';
  import type { Status, Task, UpdateStateFN, UpdateTasksFN, HeaderOptions, State } from '../../types/types.js';

  const props = defineProps<{
    state: State;
    tasks: Task[];
    updateTasks: UpdateTasksFN;
    updateState: UpdateStateFN;
  }>()

  const { updateTasks, updateState } = props;
  const { tasks } = toRefs(props);

  const name = ref<string>('');
  const status = ref<Status>('TO DO');
  const description = ref<string>('');

  watch(() => props.state.activeItem, () => {
    const id = props.state.activeItem
    const task = tasks.value.find(t => t.id === id)
    task && (name.value = task.name)
    task && (status.value = task.status as Status)
    task && (description.value = task.description)
  })

  const handleSubmit = () => {
    name.value === '' && alert('Title must not be empty')
    if (name.value.length) {
      const task = {
        name: name.value,
        status: status.value,
        description: description.value
      }
      const body = JSON.stringify(task)
      const options: HeaderOptions = {
        method: (props.state.addTask ? 'POST' : 'PATCH'),
        headers: {'Content-Type': 'application/json'},
        body
      }
      const API = props.state.addTask
      ? import.meta.env.VITE_API_URL
      : import.meta.env.VITE_API_URL + `/${props.state.activeItem}`;
      useFetch(API, options, updateTasks, updateState);

      reset();
    }
  }

  function reset() {
    name.value = '';
    status.value = 'TO DO';
    description.value = '';
    props.state.addTask = true;
    props.state.activeItem = '';
    props.state.showForm = false;
  }

</script>

<template>
  <div class="container taskform">
    <div class="backdrop" @click.self='state.showForm = false'>
      <form @submit.prevent='handleSubmit'>
        <input
          name='title'
          type="text"
          v-model.trim='name'
          placeholder="Input title..."
          max-length='250'
        >
        <select
          v-model='status'
        >
          <option value="TO DO" selected>To Do</option>
          <option value="IN PROGRESS">In Progress</option>
          <option value="DONE">Done</option>
        </select>
        <textarea
          v-model.trim='description'
          placeholder='...do something'
          max-length='1000'
        >
        </textarea>
        <button type="submit">
          {{ state.addTask ? 'Add' : 'Update'}}
        </button>
      </form>
    </div>
  </div>
</template>

<style scoped>
  div.container {
    position: absolute;
    top: 15px;
    width: 100%;
  }

  form {
    position: relative;
    top: 145px;
    background-color: var(--white);
    border-radius: 10px;
    padding: var(--space5);
    width: 40%;
    margin: auto;
    display: flex;
    flex-direction: column;
  }

  input {
    height: 50px;
    width: 100%;
    padding: var(--space2);
    color: var(--blue);
    font-weight: 800;
  }

  input[name="title"] {
    font-size: 1.2rem;
    text-align: center;
    background-color: transparent;
    border: none;
    border-bottom: solid var(--blue) 5px;
  }

  input[name="title"]:focus {
    outline: none;
  }

  select {
    width: 50%;
    height: 50px;
    margin: auto;
    text-align: center;
    appearance: none;
    background-color: var(--blue);
    color: var(--white);
    font-size: 1.2rem;
    font-weight: 700;
    border: none;
    border-radius: 2px 2px 50px 50px;
    margin-bottom: var(--space2);
  }

  select:focus {
    outline: none;
    filter: brightness(1.2);
    cursor: pointer;
  }

  option {
    background-color: var(--white);
    border: none;
    outline: none;
  }

  textarea {
    height: 200px;
    resize: none;
    margin-bottom: var(--space2);
    border: none;
    border-radius: 20px;
    padding: var(--space2);
  }

  textarea:focus {
    outline: none;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  }

  button {
    width: 30%;
    margin: auto;
    display: flex;
    justify-content: center;
    border-radius: 0;
  }

  div.backdrop {
    position: absolute;
    top: -147px;
    background-color: rgba(100, 100, 100, 0.4);
    width: 100vw;
    height: 100vh;
  }
</style>