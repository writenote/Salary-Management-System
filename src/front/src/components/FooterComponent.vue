<template>
  <div class="footer">
    <h3>Footer 내용 입력</h3>
  </div>
</template>

<script>
  export default {
    name: "FooterComponent",
  }
</script>

<style lang="scss" scoped>

  .footer {
    background-color: #969696;
    position: absolute;
    width: 100%;
    //height: 10%;
    bottom: 0;

    h3 {
      padding: 30px;
      color: white;
    }
  }
</style>
