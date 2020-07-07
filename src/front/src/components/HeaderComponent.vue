<template>
  <div class="headerComponent">
    <div class="subject">
      <h1>급여 관리 시스템</h1>
    </div>
    <nav>
      <div @click="refreshRouterView()">
        <router-link class="btn btn-primary" to="/list">일급 목록</router-link>
      </div>
      <div @click="refreshRouterView()">
        <router-link class="btn btn-primary" to="/daily">일급 계산하기</router-link>
      </div>
      <div @click="refreshRouterView()">
        <router-link class="btn btn-primary" to="/weekly">주급 계산하기</router-link>
      </div>
      <div @click="refreshRouterView()">
        <router-link class="btn btn-primary" to="/monthly">월급 계산하기</router-link>
      </div>
      <div @click="refreshRouterView()">
        <router-link class="btn btn-primary" to="/holiday">주휴수당 계산하기</router-link>
      </div>
    </nav>
  </div>
</template>

<script>
  import EventBus from "../js/EventBus";
  import {EVENT} from "../js/Constants";

  export default {
    name: "HeaderComponent",
    methods: {
      refreshRouterView() {
        EventBus.$emit(EVENT.REFRESH_ROUTER_VIEW);
      }
    }
  };
</script>

<style lang="scss" scoped>

  .headerComponent {
    min-width: 1000px;
    height: 350px;
    background-color: #A5D6A7; // rgba(28, 74, 115, 0.31); rgba(84, 180, 149, 0.51);

    .subject {
      color: white;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 250px;
    }

    nav {
      display: flex;
      justify-content: center;
      align-items: center;

      .btn {
        margin: 5px;
        width: 200px;
        text-align: center;
        font-size: 20px;
        background-color: transparent;
        border: 1px white solid;
      }
    }
  }
</style>
