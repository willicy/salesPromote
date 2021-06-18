function logout() {
  const url = "/logout";
  $.ajax({
    url: url,
    type: "GET",
    dataType: "json",
    success: function (json) {
      window.location.replace("");
    },
  });
}
function logouten() {
  const url = "/logout";
  $.ajax({
    url: url,
    type: "GET",
    dataType: "json",
    success: function (json) {
      window.location.replace("/enlogin");
    },
  });
}
