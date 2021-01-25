<template>
  <v-card color="grey darken-3" class="my-2">
    <!-- <img class="float-left ma-2" width="25" :src="imgurl" alt=""> -->
    <!-- <h3  class="float-right ma-1">{{name}}</h3> -->

    <v-row class="justify-space-between align-center">
        <img width="40" :src="imgurl" class="ml-4" />

        <h3 class="py-auto">{{ name }}, {{ health }}</h3>

        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              dark
              v-bind="attrs"
              v-on="on"
              @click="$emit('del')"
              color="error"
              class="mr-2"
              icon
              x-large
            >
              <v-icon>mdi-replay</v-icon>
            </v-btn>
          </template>
          <span>Refresh player</span>
        </v-tooltip>
    </v-row>
  </v-card>
</template>

<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  name: "player",
  props: ["name", "health", "url"],
  data: () => ({
    imgurl: "",
  }),
  mounted() {
    fetch(this.url + "id", {
      method: "POST", // *GET, POST, PUT, DELETE, etc.
      mode: "cors", // no-cors, *cors, same-origin
      cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
      credentials: "same-origin", // include, *same-origin, omit
      headers: {
        "Content-Type": "application/json",
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      redirect: "follow", // manual, *follow, error
      referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
      body: JSON.stringify({ name: this.name }),
      })
      .then((response) => response.json())
      .then((data) => {
        this.imgurl = "https://visage.surgeplay.com/face/" + data;
    });

  },
});
</script>
