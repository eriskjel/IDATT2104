<template>
  <h1>Python Code Compiler</h1>
  <div class="container">
    <form @submit.prevent="submitCode">
      <label for="input">Python Code:</label>
      <textarea name="input" id="input" rows="10" v-model="code"></textarea>
      <input type="submit" value="Compile and Run">
    </form>
    <div id="output">
      <h2>Output</h2>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {


  data() {
    return {
      code: ""
    };
  },

  methods: {
    async submitCode() {
      const response = await axios.post("http://localhost:8083/compile", {
        code: this.code
      });
      document.querySelector("#output").innerHTML = response.data;
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
body {
  font-family: Arial, sans-serif;
  background-color: #f4f4f4;
  margin: 0;
  padding: 0;
}

h1 {
  text-align: center;
  margin-top: 20px;
}

.container {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-gap: 20px;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0,0,0,0.2);
  border-radius: 5px;
}

label {
  display: block;
  font-weight: bold;
  margin-bottom: 10px;
}

textarea {
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
  resize: none;
}

input[type="submit"] {
  background-color: #4CAF50;
  color: #fff;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 5px;
  cursor: pointer;
}

input[type="submit"]:hover {
  background-color: #3e8e41;
}

#output {
  grid-column: 2 / 3;
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0,0,0,0.2);
  border-radius: 5px;
}

#output h2 {
  margin-top: 0;
}
</style>
