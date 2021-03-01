<template>
  <div class="main">
    <p>Please Select a Room</p>
    <form name="form1" id="form1" action="/action_page.php">
      Room:
      <select name="subject" id="subject">
        <option value="" selected="selected">Room Number</option>
        <!-- For loop loops through all the available room numbers and inputs these into the dropdown - these should be limited for each person, general public should only have access to first floor, students to all available rooms up to 3rd floor, staff/phd rooms to 4th floor, manager/building supervisor has access to all rooms including receptions  -->
      </select>
      <br /><br />
      <input type="submit" value="Submit" />
    </form>
  </div>
</template>

<script>
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.main {
  margin-left: 5px;
  margin-right: 5px;
}
</style>
