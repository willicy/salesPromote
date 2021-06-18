window.onload = function () {
  getAllItem();
  setLanguage();
};
let language = 0;
function setLanguage() {
  //1=en ,0=zh
  if ($("#language").val() != undefined) {
    language = 1;
  }
}
$.ajaxSetup({
  complete: function (XMLHttpRequest, textStatus) {
    // 通过XMLHttpRequest取得响应头，REDIRECT
    var redirect = XMLHttpRequest.getResponseHeader("REDIRECT"); //若HEADER中含有REDIRECT说明后端想重定向

    if (redirect == "REDIRECT") {
      var win = window;
      while (win != win.top) {
        win = win.top;
      }

      win.location.href = XMLHttpRequest.getResponseHeader("CONTEXTPATH");
    }
  },
});
function getAllItem() {
  var url = "/shop/allItem";
  $.ajax({
    url: url,
    type: "GET",
    dataType: "json",
    success: function (json) {
      $("#n-album").empty();
      var list = json.data;
      list.forEach((element) => {
        var html =
          "<div class='col' onclick='modifyModalBtn(#{id})'>" +
          "<div class='card border-0 shadow' >" +
          "<div class='card-imgframe '>" +
          "<img src='#{photoLocation}' class=' img' alt='...' /></div>" +
          "<div class='card-body'  >" +
          "<h5></h5>" +
          "<h5>#{type}</h5>" +
          "<a>￥#{price1}</a>" +
          "</div></div></div>";
        html = html.replace(/#{id}/g, element.id);
        html = html.replace("#{price1}", element.price2);
        html = html.replace("#{type}", element.type);
        html = html.replace("#{photoLocation}", element.photoLocation);
        $("#n-album").append(html);
      });
    },
  });
}
let prices = new Array(4);
//查看款式内容modal
function modifyModalBtn(value) {
  $("#n-modal-photoframe").empty();
  $("#n-form-itemmodal")[0].reset();
  $("#n-modal-inputColor").html("");
  var url = "/shop/item";
  $.ajax({
    url: url,
    data: { itemId: value },
    type: "GET",
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        modalItem = json.data;
        $("#ItemModalLabel").html(modalItem.name);

        $("#n-modal-photoframe").append(
          '<img src="' + modalItem.photoLocation + '" class="img-fluid" />'
        );
        $("#n-modal-inputUpdateTime").val(modalItem.updateTime);
        $("#n-modal-inputId").val(modalItem.id);
        $("#n-modal-inputName").val(modalItem.name);

        $("#n-modal-inputType").val(modalItem.type);
        $("#n-modal-inputSize").val(modalItem.size);
        $("#n-modal-inputPrice1").val(modalItem.price2);
        $("#n-modal-inputState").val(modalItem.state);
        $("#n-modal-inputRemark").val(modalItem.remark);

        $.ajax({
          url: "/shop/itemColorsDropdown",
          data: { itemName: modalItem.name },
          type: "GET",
          dataType: "json",
          success: function (jsonColor) {
            if (json.state == 200) {
              const list = jsonColor.data;
              var html;
              list.forEach((element) => {
                html = "<option value='#{id}' >#{data}</option>";

                html = html.replace(/#{data}/g, element.data);
                html = html.replace(/#{id}/g, element.id);
                $("#n-modal-inputColor").append(html);
                if (element.data == modalItem.color) {
                  $("#n-modal-inputColor").val(modalItem.id);
                }
              });
            } else {
              if (language == 1) {
                swal(
                  {
                    title: "Failure!",
                    text: jsonColor.message,
                    type: "error",
                    button: "Confirm",
                  },
                  function () {
                    window.location.reload();
                  }
                );
              } else {
                swal(
                  {
                    title: "查看失败!",
                    text: jsonColor.message,
                    type: "error",
                    button: "确认",
                  },
                  function () {
                    window.location.reload();
                  }
                );
              }
            }
          },
        });
        prices[0] = modalItem.price1;
        prices[1] = modalItem.price2;
        prices[2] = modalItem.price3;
        prices[3] = modalItem.price4;
        $("#num").val(3);
        $("#ItemModal").modal("show");
      } else {
        if (language == 1) {
          swal(
            {
              title: "Failure!",
              text: json.message,
              type: "error",
              button: "Confirm",
            },
            function () {
              window.location.reload();
            }
          );
        } else {
          swal(
            {
              title: "查看失败!",
              text: json.message,
              type: "error",
              button: "确认",
            },
            function () {
              window.location.reload();
            }
          );
        }
      }
    },
  });
}
//购物数量加1
$("#numUp").click(function () {
  var number = parseInt($("#num").val());
  number += 1;
  $("#num").val(number);
  if (number > 2 && number < 6) {
    $("#n-modal-inputPrice1").val(prices[1]);
  } else if (number > 5 && number < 10) {
    $("#n-modal-inputPrice1").val(prices[2]);
  } else if (number > 9) {
    $("#n-modal-inputPrice1").val(prices[3]);
  } else {
    $("#n-modal-inputPrice1").val(prices[0]);
  }
});

//购物数量-1
$("#numDown").click(function () {
  var number = parseInt($("#num").val());
  if (number == 1) {
    $("#n-modal-inputPrice1").val(prices[0]);
    return;
  }
  number -= 1;
  $("#num").val(number);

  if (number > 2 && number < 6) {
    $("#n-modal-inputPrice1").val(prices[1]);
  } else if (number > 5 && number < 10) {
    $("#n-modal-inputPrice1").val(prices[2]);
  } else if (number > 9) {
    $("#n-modal-inputPrice1").val(prices[3]);
  } else {
    $("#n-modal-inputPrice1").val(prices[0]);
  }
});

function cart() {
  const number = $("#num").val();
  const itemId = $("#n-modal-inputId").val();
  if (number < 1 || itemId == "") {
    if (language == 1) {
      swal("Failure!", "Please refresh page！", "error", { button: "Confirm" });
    } else {
      swal("失败!", "未知错误,请刷新后重试！", "error", { button: "确认" });
    }
  } else {
    $.ajax({
      url: "/shop/cart",
      type: "PUT",
      data: { itemId: itemId, number: number },
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          if (language == 1) {
            swal(
              {
                title: "Success!",
                type: "success",
                button: "Confirm",
              },
              function () {
                window.location.reload();
              }
            );
          } else {
            swal(
              {
                title: "成功加入到购物车!",
                type: "success",
                button: "确认",
              },
              function () {
                window.location.reload();
              }
            );
          }
        } else {
          if (language == 1) {
            swal("Failure!", json.message, "error", { button: "Confirm" });
          } else {
            swal("失败!", json.message, "error", { button: "确认" });
          }
        }
      },
    });
  }
}

function numInput() {
  var reg = new RegExp("^[0-9]*$");
  var n = $("#num").val();
  if (!reg.test($("#num").val())) {
    $("#num").val(3);
    $("#n-modal-inputPrice1").val(prices[1]);
    return;
  }
  let number = parseInt(n);
  if (number < 1) {
    $("#num").val(3);
    $("#n-modal-inputPrice1").val(prices[1]);
    return;
  }
  if (number > 2 && number < 6) {
    $("#n-modal-inputPrice1").val(prices[1]);
  } else if (number > 5 && number < 10) {
    $("#n-modal-inputPrice1").val(prices[2]);
  } else if (number > 9) {
    $("#n-modal-inputPrice1").val(prices[3]);
  } else {
    $("#n-modal-inputPrice1").val(prices[0]);
  }
}
//初始化
function init() {
  $("#n-btn-select").removeClass("d-none");
  $("#n-btn-create").removeClass("d-none");

  $("#n-btn-delete").addClass("d-none");
  $("#n-btn-unselect").addClass("d-none");
}
