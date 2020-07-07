<template>
  <div class="holiday">
    <div class="subject">
      <h3>주휴수당 계산하기</h3>
    </div>
    <div class="inputForm">
      <div class="weeklyTotalTimeForm">
        <label for="weeklyTotalTime">주 근무 시간</label>
        <input type="text" class="form-control" placeholder="근무 시간 입력" id="weeklyTotalTime"
               v-model="weeklyTotalTime"/>
      </div>
      <div class="hourlyWageForm">
        <label for="hourlyWage">시급</label>
        <input type="text" class="form-control" placeholder="최저시급 8,590원" id="hourlyWage"
               v-model="hourlyWage"/>
      </div>
    </div>

    <div class="btnArea">
      <button @click="calHolidayPay(weeklyTotalTime, hourlyWage)" class="btn btn-success">계산하기</button>
    </div>


    <div class="resultArea" v-show="result">
      <h5>주휴 수당은 {{ holidayPay }}원입니다.</h5>
    </div>
  </div>
</template>

<script>
  export default {
    name: "HolidayPay",
    data() {
      return {
        result: false,
        weeklyTotalTime: null,
        hourlyWage: null,
        holidayPay: 0,
      }
    },
    methods: {
      calHolidayPay(holidayTime, hourlyWage) {

        if(hourlyWage == null) {
          hourlyWage = 8590;
        } else {
          hourlyWage = parseInt(hourlyWage);
        }

        if(this.weeklyTotalTime >= 15 && this.weeklyTotalTime < 40) {
          this.holidayPay = (this.weeklyTotalTime / 40) * 8 * hourlyWage;
        } else if(this.weeklyTotalTime >= 40) {
          this.holidayPay = 8 * hourlyWage;
        }

        this.holidayPay = this.holidayPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        this.result = true;
      }
    }
  }
</script>

<style lang="scss" scoped>

  .holiday {
    margin-top: 100px;

    .subject {
      width: 1000px;
      margin: auto;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }

    .inputForm {
      justify-content: center;   // 화면 중앙
      text-align: center;
      padding: 50px 0px;
      max-width: 500px;
      min-height: 250px;
      margin: auto;

      .weeklyTotalTimeForm {

        padding-top: 30px;
        display: flex;   // 옆으로 나란히
        align-items: center;   // 위아래 중앙

        label {
          min-width: 100px;
          margin: auto;
          text-align: left;
        }
      }

      .hourlyWageForm {

        padding-top: 30px;
        display: flex;   // 옆으로 나란히
        align-items: center;   // 위아래 중앙

        label {
          min-width: 100px;
          margin: auto;
          text-align: left;
        }
      }
    }

    .btnArea {
      padding-top: 50px;
      padding-bottom: 50px;
      display: flex;
      width: 100px;
      justify-content: center;
      font-size: 18px;
      margin: 0 auto;
    }

    .resultArea {
      background-color: lightgrey;
      padding: 5% 5% 5% 15%;
      font-size: large;
      width: 100vw;
      margin: auto;
      align-items: center;
      justify-content: space-between;
    }
  }

</style>
