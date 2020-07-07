<template>
  <div class="daily">
    <div class="subject">
      <h3>일급 계산하기</h3>
    </div>
    <div class="inputForm">
      <div class="hourlyWageForm">
        <label for="hourlyWage">시급</label>
        <input type="text" class="form-control" placeholder="최저시급 8,590원" id="hourlyWage"
               v-model="hourlyWage"/>
      </div>

      <div class="startTime">
        <label for="startTime">시작 시간</label>
        <datetime type="time" id="startTime" placeholder="시간 선택"
                  value-zone="Asia/Seoul"
                  :minute-step="60"
                  :input-style="'width: 375px; border: transparent'"
                  class="form-control"
                  v-model="startTime">
        </datetime>
      </div>

      <div class="endTime">
        <label for="endTime">종료 시간</label>
        <datetime type="time" id="endTime" placeholder="시간 선택"
                  value-zone="Asia/Seoul"
                  :minute-step="60"
                  :input-style="'width: 375px; border: transparent'"
                  class="form-control"
                  v-model="endTime">
        </datetime>
      </div>
    </div>

    <div class="btnArea">
      <button @click="calWeeklyPay(hourlyWage, startTime, endTime)" class="btn btn-success">계산하기</button>
    </div>


    <div class="resultArea" v-show="result">
      <div class="area">
        <h5>주간근무 {{ dayWork }}시간</h5>
        <h5>야간근무 {{ nightWork }}시간</h5>
        <h5>하루 총 근무 시간 {{ totalWork }}시간</h5>
        <h5>총 일급은 {{ dailyPay }}원 입니다.</h5>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "DailyPay",
    data() {
      return {
        result: false,
        hourlyWage: null,
        startTime: "",
        endTime: "",
        dayWork: 0,
        nightWork: 0,
        totalWork: 0,
        dailyPay: 0
      }
    },
    methods: {
      calWeeklyPay(hourlyWage, startTime, endTime) {
        this.dayWork = 0;
        this.nightWork = 0;

        if(hourlyWage == null) {
          hourlyWage = 8590;
        } else {
          hourlyWage = parseInt(hourlyWage);
        }

        const formatStartTime = startTime.substring(11, 13);   // 2020-06-26T09:00:00.000+09:00 -> 09
        const formatEndTime = endTime.substring(11, 13);   // 12

        const workStartTime = parseInt(formatStartTime);   // 9
        const workEndTime = parseInt(formatEndTime);   // 12

        var timeList = new Array(24);
        for(var i=0; i<=23; i++) {
          timeList[i] = Boolean(false);
        }

        if(workStartTime < workEndTime) {   // 근무 시간이 하루 안에 시작 및 종료
          for(var i=workStartTime; i<workEndTime; i++) {
            timeList[i] = Boolean(true);
          }
        } else {   // 근무 시간이 자정을 넘어감
          for(var i=workStartTime; i<=23; i++) {
            timeList[i] = Boolean(true);
          }
          for(var i=0; i<workEndTime; i++) {
            timeList[i] = Boolean(true);
          }
        }


        var timeMap = new Map();
        for(var i=0; i<=23; i++) {
          if(timeList[i] == Boolean(true)) {
            if(i>=6 && i<=21) {
              timeMap.set(i, hourlyWage);   // 주간 기본 시급
              this.dayWork++;
            } else {
              timeMap.set(i, (hourlyWage*1.5));   // 야간 수당
              this.nightWork++;
            }
          }
        }

        var sum=0;
        timeMap.forEach(function (value) {
          sum += value;
        });
        this.totalWork = (this.dayWork + this.nightWork);
        this.dailyPay = sum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");;
        this.result = true;
      },
    }
  }
</script>

<style lang="scss" scoped>

  .daily {
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
      max-width: 500px;
      margin: auto;

      .hourlyWageForm {

        padding-top: 50px;
        display: flex;   // 옆으로 나란히
        align-items: center;   // 위아래 중앙

        label {
          min-width: 100px;
          margin-left: 0px;
          text-align: left;
        }

      }

      .startTime {
        padding-top: 50px;
        display: flex;
        align-items: center;

        label {
          min-width: 100px;
          margin-left: 0px;
          text-align: left;
        }
      }

      .endTime {
        padding-top: 50px;
        display: flex;
        align-items: center;

        label {
          min-width: 100px;
          margin-left: 0px;
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

      .area {
        width: 500px;
        margin-left: 30px;
        font-size: 30px;
        background-color: transparent;
        border: 2px white solid;
        padding: 10px;
      }
    }
  }
</style>
