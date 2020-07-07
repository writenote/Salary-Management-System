<template>
  <div class="addForm">
    <div v-if="!submitted">
      <h3>근무 시간 입력하기</h3>

      <div class="hourlyWageForm">
        <label for="hourlyWage">시급</label>
        <input type="text" class="form-control" placeholder="최저시급 8,590원" id="hourlyWage"
               v-model="salary.hourlyWage"/>
      </div>

      <div class="dateTimePicker">
        <div class="startTime">
          <label for="startTime">시작 시간</label>
          <datetime type="datetime" id="startTime"
                    v-model="salary.startTime" placeholder="날짜 선택"
                    value-zone="Asia/Seoul"
                    :minute-step="60"
                    :input-style="'width: 375px; border: transparent'"
                    class="form-control"
          ></datetime>
        </div>

        <div class="endTime">
          <label for="endTime">종료 시간</label>
          <datetime type="datetime" id="endTime"
                    v-model="salary.endTime" placeholder="날짜 선택"
                    value-zone="Asia/Seoul"
                    :minute-step="60"
                    :input-style="'width: 375px; border: transparent'"
                    class="form-control"
          ></datetime>
        </div>
      </div>

      <button @click="addRow" class="btn btn-success">등록</button>
    </div>

    <div class="submitted" v-else>
      <h4>근무시간이 등록되었습니다.</h4>
      <router-link to="/list">
        <button class="btn btn-success">목록 확인</button>
      </router-link>
    </div>
  </div>
</template>

<script>
  import ApiSvc from "../js/ApiSvc";

  export default {
    name: "AddSalary",
    data() {
      return {
        salary: {
          id: 0,
          hourlyWage: "",
          startTime: "",
          endTime: "",
        },
        submitted: false,
      }
    },
    methods: {
      addRow() {
        const requestData = {
          hourlyWage: this.salary.hourlyWage,
          startTime: this.salary.startTime,
          endTime: this.salary.endTime
        };

        ApiSvc.post("/add", requestData)
          .then(res => {
            this.salary.id = res.data.id;
          })
          .catch(e => console.log(e));

        this.submitted = true;
      },
    }
  };
</script>

<style lang="scss" scoped>

  .addForm {
    margin: auto;
    padding-top: 5%;
    justify-content: center;   // 화면 중앙
    text-align: center;
    max-width: 500px;

    div {

      .hourlyWageForm {

        padding-top: 50px;
        display: flex;   // 옆으로 나란히
        align-items: center;   // 위아래 중앙

        label {
          min-width: 100px;
          margin: auto;
          text-align: left;
        }

      }

      .dateTimePicker {

        .startTime {
          padding-top: 50px;
          display: flex;
          align-items: center;

          label {
            min-width: 100px;
            margin: auto;
            text-align: left;
          }
        }

        .endTime {
          padding-top: 50px;
          display: flex;
          align-items: center;

          label {
            min-width: 100px;
            margin: auto;
            text-align: left;
          }
        }
      }

      .btn {
        margin: 50px;
      }
    }

    div > .btn{
      margin: 100px;
    }

    .submitted {
      padding-top: 100px;
      min-height: 500px;
    }
  }
</style>
