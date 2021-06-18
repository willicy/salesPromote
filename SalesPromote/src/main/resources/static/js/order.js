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
  var url = "/shop/orderItems";
  $.ajax({
    url: url,
    type: "GET",
    dataType: "json",
    success: function (json) {
      $("#n-order-container").empty();
      let list = json.data;

      list.forEach((firstlayer) => {
        let element = firstlayer[0];
        let items = firstlayer[1];

        let allitemhtml = "";

        items.forEach((item) => {
          let itemhtml =
            '<div align="center"style="    margin-right: 12px;min-width: 100px; overflow: hidden">' +
            '<div class="card-imgframe" onclick="infomodal(#{i-id})">' +
            '<img src="#{i-photoLocation}"class="img"alt="..."/> </div>' +
            "#{i-name} </div>";
          itemhtml = itemhtml.replace(/#{i-id}/g, item.id);
          itemhtml = itemhtml.replace("#{i-photoLocation}", item.photoLocation);
          itemhtml = itemhtml.replace("#{i-name}", item.name);
          allitemhtml += itemhtml;
        });
        var html = "";
        if (language == 1) {
          html =
            '<div class="row border mb-4">' +
            '<div class="col bg-light " style="color: gray">' +
            '<div class="col">#{o-createTime}</div>' +
            '<div class="col  d-flex flex-row">Order No:<div style="color: black">#{o-id}</div></div>' +
            '<div class="col"></div><div class="col d-flex flex-row">Total:<div style="color: black">#{o-prices}</div></div>' +
            '<div class="col  d-flex flex-row">State:<div style="color: #{o-color}">#{o-state}</div></div></div>' +
            '<div class="border-top pt-3 orderitem-container" >' +
            allitemhtml +
            "</div></div>";
          html = html.replace(/#{o-id}/g, "ddh" + element.id);
          html = html.replace("#{o-createTime}", element.createTime);
          html = html.replace("#{o-prices}", element.prices);

          if (element.state == 1) {
            html = html.replace("#{o-state}", "paid");
            html = html.replace("#{o-color}", "black");
          } else {
            html = html.replace("#{o-state}", "unpaid");
            html = html.replace("#{o-color}", "red");
          }
        } else {
          html =
            '<div class="row border mb-4">' +
            '<div class="col bg-light" style="color: gray">' +
            '<div class="col">#{o-createTime}</div>' +
            '<div class="col  d-flex flex-row">订单号：<div style="color: black">#{o-id}</div></div>' +
            '<div class="col"></div><div class="col d-flex flex-row">订单金额：<div style="color: black">#{o-prices}</div></div>' +
            '<div class="col d-flex flex-row">状态：<div style="color: #{o-color}">#{o-state}</div></div></div>' +
            '<div class="border-top pt-3 orderitem-container" >' +
            allitemhtml +
            "</div></div>";
          html = html.replace(/#{o-id}/g, "ddh" + element.id);
          html = html.replace("#{o-createTime}", element.createTime);
          html = html.replace("#{o-prices}", element.prices);

          if (element.state == 1) {
            html = html.replace("#{o-state}", "已付款");
            html = html.replace("#{o-color}", "black");
          } else {
            html = html.replace("#{o-state}", "未付款");
            html = html.replace("#{o-color}", "red");
          }
        }

        $("#n-order-container").append(html);
      });
    },
  });
}
function infomodal(id) {
  $("#n-modal-photoframe").empty();
  $("#n-form-itemmodal")[0].reset();

  var url = "/shop/orderItem";

  $.ajax({
    url: url,
    data: { orderItemId: id },
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
        $("#n-modal-inputColor").val(modalItem.color);
        $("#n-modal-inputType").val(modalItem.type);
        $("#n-modal-inputSize").val(modalItem.size);
        $("#n-modal-inputPrice1").val(modalItem.orderPrice);
        $("#n-modal-inputState").val(modalItem.state);
        $("#n-modal-inputRemark").val(modalItem.remark);
        $("#n-modal-inputTotal").val("￥" + modalItem.orderTotal);
        $("#n-modal-inputNum").val(modalItem.orderAmount);
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
