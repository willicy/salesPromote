window.onload = function () {
  getAllItem();
};
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
$(".form_date").datetimepicker({
  language: "zh-CN",
  weekStart: 1,
  todayBtn: 1,
  autoclose: 1,
  todayHighlight: 1,
  startView: 2,
  minView: 2,
  forceParse: 0,
});
function getAllItem() {
  var url = "/user/allorder/orderItems";

  $.ajax({
    url: url,
    data: {
      startTime: $("#startTime").val(),
      endTime: $("#endTime").val(),
      state:
        $("#n-state").val() == ""
          ? undefined
          : $("#n-state").val() == "1"
          ? true
          : false,
      customer: $("#n-customer").val(),
    },
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
        //<div style="color: #{o-color}">#{o-state}</div>
        var html =
          '<div class="row border mb-4">' +
          '<div class="col bg-light d-md-flex flex-md-row" style="color: gray">' +
          '<div class="col col-md-3">#{o-createTime}</div>' +
          '<div class="col col-md-2 col-lg-2 d-flex flex-column flex-lg-row">订单号：<div style="color: black">#{o-lid}</div></div>' +
          '<div class="col col-md-2 col-lg-3 d-flex flex-column flex-lg-row">客户：<div style="color: black">#{o-customer}</div></div>' +
          '<div class="col col-md-3 col-lg-2 d-flex flex-column flex-lg-row">订单金额：<div style="color: black">#{o-prices}</div></div>' +
          '<div class="col col-md-2  d-flex flex-column flex-lg-row">状态：' +
          '<select onchange="changeState(#{o-id},this)" class="form-select form-select-sm w-auto"  aria-label="Default select example"  id="n-state"  >' +
          '<option value="1"#{o-state-1}>已付款</option><option value="0"#{o-state-0}>未付款</option></select></div></div>' +
          '<div class="border-top pt-3 orderitem-container" >' +
          allitemhtml +
          "</div></div>";
        html = html.replace(/#{o-lid}/g, "ddh" + element.id);
        html = html.replace(/#{o-id}/g, element.id);
        html = html.replace("#{o-createTime}", element.createTime);
        html = html.replace("#{o-prices}", element.prices);
        html = html.replace("#{o-customer}", element.customer);
        if (element.state == 1) {
          html = html.replace("#{o-state-1}", "selected");
          html = html.replace("#{o-state-0}", "");
        } else {
          html = html.replace("#{o-state-0}", "selected");
          html = html.replace("#{o-state-1}", "");
        }

        $("#n-order-container").append(html);
      });
    },
  });
}
function infomodal(id) {
  $("#n-modal-photoframe").empty();
  $("#n-form-itemmodal")[0].reset();

  var url = "/user/allorder/orderItem";

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
    },
  });
}
function changeState(id, value) {
  var url = "/user/allorder/order";
  $.ajax({
    url: url,
    data: {
      orderId: id,
      state:
        $(value).val() == "" ? undefined : $(value).val() == "1" ? true : false,
    },
    type: "PATCH",
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        getAllItem();
      } else {
        swal(
          {
            title: "更改失败!",
            text: json.message,
            type: "error",
            button: "确认",
          },
          function () {
            window.location.reload();
          }
        );
      }
    },
  });
}
