<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <div class="d-flex align-center">
        <h1>CommandAndControl</h1>
      </div>

      <v-spacer></v-spacer>

      <v-btn color="success" @click="refreshjobs()">Get jobs</v-btn>
    </v-app-bar>

    <v-main>
      <v-row>
        <v-col>
          <v-card class="ma-4">
            <v-card-title primary-title> Send command </v-card-title>
            <v-form
              v-model="valid"
              @submit="sendcommand(send)"
              onSubmit="return false;"
            >
              <v-row>
                <v-col>
                  <v-text-field
                    class="mx-4"
                    v-model="send"
                    label="Command to send"
                    required
                    :rules="commandRules"
                  ></v-text-field>
                </v-col>
                <v-col cols="auto">
                  <v-btn
                    class="ma-4"
                    color="success"
                    :disabled="!valid"
                    :loading="loading"
                    outlined
                    @click="sendcommand(send)"
                    >Send</v-btn
                  >
                </v-col>
              </v-row>
            </v-form>

            <div v-if="jobs.length">
              <span class="mx-4">Previous Commands:</span>
              <v-btn @click="del(-1)" color="error" class="float-right mr-4"
                >Remove all</v-btn
              >
              <div class="py-2">
                <command
                  class="my-4"
                  v-for="(job, index) in jobs"
                  :key="index"
                  :cmd="job"
                  @send="sendcommand(job)"
                  @del="del(index)"
                />
              </div>
            </div>
          </v-card>
        </v-col>
        <v-col cols="4" v-if="users.length">
          <v-card class="ma-4 pa-4 ml-n4">
            <v-card-title class="ml-n4" primary-title> Players: </v-card-title>
            <v-row>
              <v-col v-for="(user, index) in users" :key="index" cols="12">
                <player :name="user" :url="url" @del="deluser(index)" />
                <!-- <v-card color="success" >text</v-card> -->
              </v-col>
            </v-row>
          </v-card>
        </v-col>
      </v-row>
    </v-main>
  </v-app>
</template>

<script lang="ts">
import Vue from "vue";
import command from "./components/command.vue";
import player from "./components/player.vue";

export default Vue.extend({
  name: "App",

  components: {
    command,
    player,
  },

  data: () => ({
    send: "",
    valid: true,
    loading: false,
    commandRules: [(v: string) => !!v || "Command is required"],
    jobs: ["aaa"],
    users: [
      "Tokfrans03",
      "Tokfrans03",
      "Tokfrans03",
      "Tokfrans03",
      "Tokfrans03",
      "Tokfrans03",
    ],
    auto: 0,
    url: "",
  }),
  created() {
    const url = new URL(window.location.href);
    url.port = "8000";
    url.pathname = "";
    this.url = url.toString();

    this.auto = setInterval(() => {
      this.refreshjobs();
    }, 1000);
  },
  methods: {
    async deluser(index: number) {
      fetch(this.url + "login", {
        method: "DELETE", // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
          "Content-Type": "application/json",
          // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: "follow", // manual, *follow, error
        referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        body: JSON.stringify({ i: index }), // body data type must match "Content-Type" header
      });
    },
    async refreshjobs() {
      fetch(this.url + "jobs", {
        method: "GET", // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
          "Content-Type": "application/json",
          // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: "follow", // manual, *follow, error
        referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
      })
        .then((response) => response.json())
        .then((data) => {
          this.jobs = data.jobs;
        });
      fetch(this.url + "login", {
        method: "GET", // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
          "Content-Type": "application/json",
          // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: "follow", // manual, *follow, error
        referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
      })
        .then((response) => response.json())
        .then((data) => {
          this.users = data;
        });
    },
    async del(index: number) {
      fetch(this.url + "jobs", {
        method: "DELETE", // *GET, POST, PUT, DELETE, etc.
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "same-origin", // include, *same-origin, omit
        headers: {
          "Content-Type": "application/json",
          // 'Content-Type': 'application/x-www-form-urlencoded',
        },
        redirect: "follow", // manual, *follow, error
        referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        body: JSON.stringify({ i: index }), // body data type must match "Content-Type" header
      });
    },
    async sendcommand(command: string) {
      this.loading = true;
      await fetch(this.url + "jobs", {
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
        body: JSON.stringify({ command: command }), // body data type must match "Content-Type" header
      });
      this.loading = false;
    },
  },
});
</script>
