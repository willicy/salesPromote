window.onload = function () {
  getAllItem().then(function () {
    checkAll();
  });
  $("#checkAll").prop("checked", true);

  setLanguage();
};
let language = 0;
function setLanguage() {
  //1=en ,0=zh
  if ($("#language").val() != undefined) {
    language = 1;
  }
}
let pricemap = new Map();
let sizemap = new Map();
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
  var url = "/shop/allCartItem";
  return $.ajax({
    url: url,
    //async: false,
    type: "GET",
    dataType: "json",
    success: function (json) {
      $("#n-tbody").empty();
      var list = json.data;
      let deleteText = "删除";
      if (language == 1) {
        deleteText = "delete";
      }
      list.forEach((element) => {
        var html =
          "<tr id='#{id}'>" +
          "<th scope='col' scope='row' style='vertical-align: middle;'>" +
          "<input type='checkbox' style='cursor: pointer' value='#{id}' onclick='check()'/>" +
          "</th>" +
          "<td scope='col' data-bs-toggle = 'modal' data-bs-target='#CustomerModal'><img style='width:90px;' src='#{photoLocation}' class=' img' alt='...' /></td>" +
          "<td scope='col'style='vertical-align: middle;'class='d-none d-xl-table-cell'></td>" +
          "<td scope='col'style='vertical-align: middle;'><a name='price'>￥#{price}</a></td>" +
          "<td scope='col'style='vertical-align: middle;'><button type='button'onclick='numDown(#{id},&apos;#{updateTime}&apos;)' class='btn btn-sm btn-primary'>-</button>" +
          "<input type='text' class='mx-1 form-control form-control-sm'name='num'style='display: inline-block; width:50px;' onchange='numInput(#{id},&apos;#{updateTime}&apos;)'/>" +
          "<button type='button' class='btn btn-sm btn-primary' onclick='numUp(#{id},&apos;#{updateTime}&apos;)' >+</button>" +
          "</td>" +
          "<td scope='col'style='vertical-align: middle;'><a name='sizeNum'>#{sizeNum}</a></td>" +
          "<td  scope='col'style='vertical-align: middle;' name='pieceNum'><a >#{pieceNum}</a></td>" +
          "<td  scope='col'style='vertical-align: middle;'><a name='totalprice'>￥#{total}</a></td>" +
          "<td  scope='col'style=' vertical-align: middle;'><a style='cursor:pointer;color: gray;font-size: 13px;'onclick='deleteItem(#{id})'>" +
          deleteText +
          "</a></td></tr>";
        html = html.replace(/#{id}/g, element.id);
        html = html.replace(/#{updateTime}/g, element.updateTime);
        html = html.replace("#{photoLocation}", element.photoLocation);
        html = html.replace("#{sizeNum}", element.sizeNum);
        html = html.replace("#{pieceNum}", element.sizeNum * element.number);
        let prices = new Array();
        prices[0] = element.price1;
        prices[1] = element.price2;
        prices[2] = element.price3;
        prices[3] = element.price4;

        if (element.number > 2 && element.number < 6) {
          html = html.replace("#{price}", element.price2);
          html = html.replace(
            "#{total}",
            element.number * element.sizeNum * element.price2
          );
        } else if (element.number > 5 && element.number < 10) {
          html = html.replace("#{price}", element.price3);
          html = html.replace(
            "#{total}",
            element.number * element.sizeNum * element.price3
          );
        } else if (element.number > 9) {
          html = html.replace("#{price}", element.price4);
          html = html.replace(
            "#{total}",
            element.number * element.sizeNum * element.price4
          );
        } else {
          html = html.replace("#{price}", element.price1);
          html = html.replace(
            "#{total}",
            element.number * element.sizeNum * element.price1
          );
        }
        sizemap.set(element.id, element.sizeNum);
        pricemap.set(element.id, prices);

        $("#n-tbody").append(html);
        $("#" + element.id + " [name='num']").val(element.number);
      });
    },
  });
}

function addOrder() {
  let itemIds = new Array();

  document
    .querySelectorAll("#n-tbody input[type=checkbox]")
    .forEach(function (checkbox) {
      if ($(checkbox).prop("checked")) {
        itemIds.push($(checkbox).prop("value"));
      }
    });
  if (itemIds.length > 0) {
    var url = "/shop/order";
    $.ajax({
      url: url,
      data: {
        itemIds: JSON.stringify(itemIds),
      },
      type: "put",
      dataType: "json",
      success: function (json) {
        if (language == 1) {
          window.location.replace("/shop/enorder");
        } else {
          window.location.replace("/shop/order");
        }
      },
    });
  } else {
    if (language == 1) {
      swal("Failure!", "Please select product!", "error", {
        button: "Confirm",
      });
    } else {
      swal("下单失败!", "请选择要下单的商品", "error", { button: "确认" });
    }
  }
}
function deleteItem(id) {
  var url = "/shop/cart";
  $.ajax({
    url: url,
    data: { itemId: id },
    type: "DELETE",
    dataType: "json",
    success: function (json) {
      window.location.reload();
    },
  });
}
//购物数量加1
function numUp(id, updateTime) {
  let number = parseInt($("#" + id + " [name='num']").val());
  let prices = pricemap.get(id);
  let sizenum = sizemap.get(id);
  if (prices == undefined) {
    if (language == 1) {
      swal("Failure!", "Please refresh page!", "error", { button: "确认" });
    } else {
      swal("操作失败!", "请刷新页面重试", "error", { button: "确认" });
    }
    return;
  }

  number += 1;
  let url = "/shop/cart";
  $.ajax({
    url: url,
    data: { itemId: id, number: number, updateTime: updateTime },
    type: "PATCH",
    dataType: "json",
    success: function (json) {
      if (json.state == 405) {
        if (language == 1) {
          swal(
            {
              title: "Failure!",
              text: "This product already modified ,Please refresh page!",
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
              title: "操作失败!",
              text: json.message,
              type: "error",
              button: "确认",
            },
            function () {
              window.location.reload();
            }
          );
        }

        return;
      }
      if (json.state != 200) {
        if (language == 1) {
          swal({
            title: "Failure!",
            text: json.message,
            type: "error",
            button: "Confirm",
          });
        } else {
          swal({
            title: "操作失败!",
            text: json.message,
            type: "error",
            button: "确认",
          });
        }
        return;
      }
      $("#" + id + " [name='pieceNum']").html(sizemap.get(Number(id)) * number);
      $("#" + id + " [name='num']").val(number);
      if (number > 2 && number < 6) {
        $("#" + id + " [name='price']").html("￥" + prices[1]);
        $("#" + id + " [name='totalprice']").html(
          "￥" + prices[1] * sizenum * number
        );
      } else if (number > 5 && number < 10) {
        $("#" + id + " [name='price']").html("￥" + prices[2]);
        $("#" + id + " [name='totalprice']").html(
          "￥" + prices[2] * sizenum * number
        );
      } else if (number > 9) {
        $("#" + id + " [name='price']").html("￥" + prices[3]);
        $("#" + id + " [name='totalprice']").html(
          "￥" + prices[3] * sizenum * number
        );
      } else {
        $("#" + id + " [name='price']").html("￥" + prices[0]);
        $("#" + id + " [name='totalprice']").html(
          "￥" + prices[0] * sizenum * number
        );
      }
      check();
    },
  });
}

//购物数量-1
function numDown(id, updateTime) {
  var number = parseInt($("#" + id + " [name='num']").val());
  let url = "/shop/cart";
  let prices = pricemap.get(id);
  let sizenum = sizemap.get(id);
  if (prices == undefined) {
    if (language == 1) {
      swal("Failure!", "Please refresh page!", "error", { button: "Confirm" });
    } else {
      swal("操作失败!", "请刷新页面重试", "error", { button: "确认" });
    }
    return;
  }
  if (number == 1) {
    $("#" + id + " [name='price']").html("￥" + prices[0]);
    return;
  }
  number -= 1;
  let itemIds = new Array();

  document
    .querySelectorAll("#n-tbody input[type=checkbox]")
    .forEach(function (checkbox) {
      if ($(checkbox).prop("checked")) {
        itemIds.push($(checkbox).prop("value"));
      }
    });
  $.ajax({
    url: url,
    data: { itemId: id, number: number, updateTime: updateTime },
    type: "PATCH",
    dataType: "json",
    success: function (json) {
      if (json.state == 405) {
        if (language == 1) {
          swal(
            {
              title: "Failure!",
              text: "This product already modified ,Please refresh page!",
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
              title: "操作失败!",
              text: json.message,
              type: "error",
              button: "确认",
            },
            function () {
              window.location.reload();
            }
          );
        }

        return;
      }
      if (json.state != 200) {
        if (language == 1) {
          swal({
            title: "Failure!",
            text: json.message,
            type: "error",
            button: "Confirm",
          });
        } else {
          swal({
            title: "操作失败!",
            text: json.message,
            type: "error",
            button: "确认",
          });
        }
        return;
      }

      $("#" + id + " [name='num']").val(number);
      $("#" + id + " [name='pieceNum']").html(sizemap.get(Number(id)) * number);
      if (number > 2 && number < 6) {
        $("#" + id + " [name='price']").html("￥" + prices[1]);
        $("#" + id + " [name='totalprice']").html(
          "￥" + prices[1] * sizenum * number
        );
      } else if (number > 5 && number < 10) {
        $("#" + id + " [name='price']").html("￥" + prices[2]);
        $("#" + id + " [name='totalprice']").html(
          "￥" + prices[2] * sizenum * number
        );
      } else if (number > 9) {
        $("#" + id + " [name='price']").html("￥" + prices[3]);
        $("#" + id + " [name='totalprice']").html(
          "￥" + prices[3] * sizenum * number
        );
      } else {
        $("#" + id + " [name='price']").html("￥" + prices[0]);
        $("#" + id + " [name='totalprice']").html(
          "￥" + prices[0] * sizenum * number
        );
      }
      check();
    },
  });
}

function numInput(id, updateTime) {
  var reg = new RegExp("^[0-9]*$");
  var n = $("#" + id + " [name='num']").val();

  let prices = pricemap.get(id);
  let sizenum = sizemap.get(id);
  if (prices == undefined) {
    swal("操作失败!", "请刷新页面重试", "error", { button: "确认" });
    return;
  }
  let flag = false;

  if (!reg.test(n)) {
    flag = true;
    n = 3;
  }
  let number = parseInt(n);
  if (number < 1) {
    flag = true;
    number = 3;
  }
  let url = "/shop/cart";
  $.ajax({
    url: url,
    data: { itemId: id, number: number, updateTime: updateTime },
    type: "PATCH",
    dataType: "json",
    success: function (json) {
      if (json.state == 405) {
        if (language == 1) {
          swal(
            {
              title: "Failure!",
              text: "This product already modified ,Please refresh page!",
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
              title: "操作失败!",
              text: json.message,
              type: "error",
              button: "确认",
            },
            function () {
              window.location.reload();
            }
          );
        }

        return;
      }
      if (json.state != 200) {
        if (language == 1) {
          swal({
            title: "Failure!",
            text: json.message,
            type: "error",
            button: "Confirm",
          });
        } else {
          swal({
            title: "操作失败!",
            text: json.message,
            type: "error",
            button: "确认",
          });
        }
        return;
      }
      if (flag == true) {
        $("#" + id + " [name='num']").val(3);
        $("#" + id + " [name='price']").html("￥" + prices[1]);
        $("#" + id + " [name='totalprice']").html("￥" + prices[1] * 3);

        check();
        return;
      }
      $("#" + id + " [name='pieceNum']").html(sizemap.get(Number(id)) * number);
      if (number > 2 && number < 6) {
        $("#" + id + " [name='price']").html("￥" + prices[1]);
        $("#" + id + " [name='totalprice']").html(
          "￥" + prices[1] * sizenum * number
        );
      } else if (number > 5 && number < 10) {
        $("#" + id + " [name='price']").html("￥" + prices[2]);
        $("#" + id + " [name='totalprice']").html(
          "￥" + prices[2] * sizenum * number
        );
      } else if (number > 9) {
        $("#" + id + " [name='price']").html("￥" + prices[3]);
        $("#" + id + " [name='totalprice']").html(
          "￥" + prices[3] * sizenum * number
        );
      } else {
        $("#" + id + " [name='price']").html("￥" + prices[0]);
        $("#" + id + " [name='totalprice']").html(
          "￥" + prices[0] * sizenum * number
        );
      }

      check();
    },
  });
}
function check() {
  let sum = 0;

  let prices;
  let sizenum;
  getSelect().forEach(function (number, id) {
    sizenum = sizemap.get(Number(id));
    prices = pricemap.get(Number(id));
    if (prices == undefined) {
      swal("操作失败!", "请刷新页面重试", "error", { button: "确认" });
      return;
    }
    if (number > 2 && number < 6) {
      sum = sum + prices[1] * sizenum * number;
    } else if (number > 5 && number < 10) {
      sum = sum + prices[2] * sizenum * number;
    } else if (number > 9) {
      sum = sum + prices[3] * sizenum * number;
    } else {
      sum = sum + prices[0] * sizenum * number;
    }
  });

  $("#total-count").html(getSelect().size);
  $("#total-price").html(sum);
}
//全选及反选
function checkAll() {
  if ($("#checkAll").prop("checked") == true) {
    $("#n-tbody input[type=checkbox]").each(function () {
      $(this).prop("checked", true);
    });
  } else {
    $("#n-tbody input[type=checkbox]").each(function () {
      $(this).prop("checked", false);
    });
  }
  check();
}
function getSelect() {
  let items = new Map();
  $("#n-tbody input[type=checkbox]").each(function () {
    if ($(this).prop("checked") == true) {
      items.set($(this).val(), $("#" + $(this).val() + " [name='num']").val());
    }
  });

  return items;
}
