<template>
  <v-card color="grey darken-3" class="mx-4 pa-2">
    <v-row>
      <v-col cols="auto">
        <h2>
          {{ cmd }}
        </h2>
      </v-col>
      <v-col>
        <v-tooltip bottom>  
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              dark
              v-bind="attrs"
              v-on="on"
              @click="$emit('del')"
              color="error"
              icon
              class="float-right"
              >
              <v-icon>mdi-delete</v-icon>
            </v-btn>
          </template>
          <span>Remove command</span>
        </v-tooltip>

        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              dark
              v-bind="attrs"
              v-on="on"
              @click="$emit('send')"
              color="success"
              icon
              class="float-right mr-8"
            >
              <v-icon>mdi-replay</v-icon>
            </v-btn>
          </template>
          <span>Resend command</span>
        </v-tooltip>
      </v-col>
    </v-row>
  </v-card>
</template>

<script lang="ts">
export default {
  name: "command",
  props: ["cmd"],
};
</script>

<style>
</style>