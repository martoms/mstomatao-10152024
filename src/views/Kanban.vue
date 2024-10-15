<script setup lang="ts">

</script>

<template>
  <header>
    <a href="https://www.linkedin.com/in/m-tomatao/" target="_blank">
      <img src="@/assets/marjohn-banner.svg" alt="marjohn">
    </a>
    <h1 id="kanban">Kanban</h1>
  </header>
  <main>
    <button id="createtask" class="taskform">
      <img src="@/assets/add-w.svg" alt="add.svg">
      Create Task
    </button>
    
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
