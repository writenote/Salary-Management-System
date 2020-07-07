<template>
  <div id="app">
    <div class="home" v-if="home">
      <div class="imageArea">
        <fade-images :delay="5000" :height="'100%'" :width="'100%'"
                     :images="images"/>
        <div class="image-cover"></div>
        <div class="subject">
          <h1>급여 계산기</h1>
          <button @click="goMenu()" class="btn">계산하러 가기</button>
        </div>
      </div>

      <div class="intro">
        <h1>급여 계산기는 5가지 기능을 제공합니다.</h1>
        <div class="boxes">
          <div class="box">
            <h2>일급 목록</h2>
            <img src="../../front/src/assets/icons/notepad.png">
            <h4>매일 근무 시간을 입력</h4>
            <h4>날짜별 일급 확인</h4>
            <h4>월별로 일급 누적액 확인</h4>
          </div>
          <div class="box">
            <h2>일급 계산하기</h2>
            <img src="../../front/src/assets/icons/clock.png">
            <h4>하루 근무 시간과 시급 입력</h4>
            <h4>야간수당이 자동 계산된<br> 일급 확인</h4>
          </div>
          <div class="box">
            <h2>주급 계산하기</h2>
            <img src="../../front/src/assets/icons/calendar.png">
            <h4>하루 근무 시간과 시급,<br> 주 근무 일수 입력</h4>
            <h4>주휴수당이 포함된<br> 주급 확인</h4>
          </div>
          <div class="box">
            <h2>월급 계산하기</h2>
            <img src="../../front/src/assets/icons/day.png">
            <h4>하루 근무 시간과 시급,<br> 월 근무 일수 입력</h4>
            <h4>주휴수당이 포함된<br> 월급 확인</h4>
          </div>
          <div class="box">
            <h2>주휴수당 계산하기</h2>
            <img src="../../front/src/assets/icons/business-and-finance.png">
            <h4>주 근무 시간과 시급 입력</h4>
            <h4>주휴수당 확인</h4>
          </div>
        </div>
      </div>

      <div class="footer">
        <h4>Made by Seung Min, Han Sol</h4>
      </div>
    </div>

    <div class="else" v-else>
      <header-component/>
      <div class="empty" v-if="empty"></div>

      <transition class="content" name="component-fade" mode="out-in">
        <router-view :key="key"/>
      </transition>

      <div class="footer">
        <h4>Made by Seung Min, Han Sol</h4>
      </div>
    </div>
  </div>
</template>

<script>
  import HeaderComponent from "./components/HeaderComponent";
  import FooterComponent from "./components/FooterComponent";
  import SalaryList from "./components/SalaryList";
  import AddSalary from "./components/AddSalary";
  import DailyPay from "./components/pay/DailyPay";
  import WeeklyPay from "./components/pay/WeeklyPay";
  import MonthlyPay from "./components/pay/MonthlyPay";
  import HolidayPay from "./components/pay/HolidayPay";
  import EventBus from "./js/EventBus";
  import {EVENT} from "./js/Constants";

  import Vue from "vue";
  import VueRouter from 'vue-router';

  import Datetime from 'vue-datetime';
  import 'vue-datetime/dist/vue-datetime.css';
  import {Settings} from 'luxon';
  import FadeImages from 'vue-fade-images';

  Vue.use(Datetime);
  Settings.defaultLocale = 'ko';

  const router = new VueRouter({
    mode: 'history',
    routes: [
      {path: '/list', component: SalaryList},
      {path: '/addSalary', component: AddSalary},
      {path: '/daily', component: DailyPay},
      {path: '/weekly', component: WeeklyPay},
      {path: '/monthly', component: MonthlyPay},
      {path: '/holiday', component: HolidayPay}
    ]
  });

  export default {
    name: 'app',
    data () {
      return {
        key: 0,
        home: true,
        images: [   // ----- 사진 고르기 -----
          {src: require("../../front/src/assets/red-pencil.jpg")},
          {src: require("../../front/src/assets/working-people.jpg")},
          {src: require("../../front/src/assets/calculator.jpg")},
          {src: require("../../front/src/assets/checkcard.jpg")},
          {src: require("../../front/src/assets/bank-book.jpg")},
        ],
        empty: true
      }
    },
    created() {
      EventBus.$on(EVENT.REFRESH_ROUTER_VIEW, () => {
        this.key++;
        this.empty = false;
      });
    },
    router,
    components: {
      HeaderComponent,
      FooterComponent,
      SalaryList,
      AddSalary,
      DailyPay,
      WeeklyPay,
      MonthlyPay,
      HolidayPay,
      FadeImages
    },
    methods: {
      goMenu() {
        this.home = false;
      }
    }
  };
</script>

<style lang="scss" scoped>

  #app {
    font-family: 'NanumSquare', sans-serif;

    .home {
      overflow-x: hidden;
      position: relative;

      .imageArea {
        width: 100vw;
        height: 100vh;
        background-repeat: no-repeat;
        background-position: center center;
        position: relative;

        .image-cover {
          width: 100%;
          height: 100%;
          background-color: rgba(155, 114, 41, 0.31);
          position: absolute;
          z-index: 1;
        }

        .subject {
          width: 100%;
          height: 100%;
          text-align: center;
          position: absolute;
          z-index: 2;

          h1 {
            width: 100vw;
            margin: 15% auto;
            font-size: 90px;
            color: white;
          }

          .btn {
            width: 300px;
            border: 3px white solid;
            background-color: transparent;
            text-align: center;
            font-size: 40px;
            color: white;
          }
        }
      }

      .intro {
        background-color: rgba(253, 199, 67, 0.57);
        padding: 120px 60px;
        align-items: center;
        text-align: center;

        .boxes {
          margin-top: 30px;
          display: flex;

          .box {
            width: 470px;
            height: 450px;
            margin: 20px;
            padding: 25px;
            background-color: white;
            border-radius: 20px;

            h2 {
              margin: 8px 0px;
              font-weight: bold;
            }

            img {
              width: 130px;
              height: 130px;
              margin: 30px;
            }

            h4 {
              margin-top: 15px;
            }
          }
        }
      }

      .footer {
        height: 170px;
        background-color: #123f5a;
        padding: 40px;

        h4 {
          margin: 0;
          padding: 30px;
          color: white;
          text-align: right;
        }
      }
    }

    .else {

      .empty {
        min-height: 500px;
      }

      .footer {
        height: 170px;
        background-color: #123f5a;
        padding: 40px;

        h4 {
          margin: 0;
          padding: 30px;
          color: white;
          text-align: right;
        }
      }
    }
  }

  .component-fade-leave-active {
    opacity: 0;
  }

  .component-fade-enter, .component-fade-leave-to {
    opacity: 0;
  }

  .component-fade-enter-active {
    transition: opacity .5s ease;
  }
</style>
