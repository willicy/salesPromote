window.onload = function () {
  init();
  getAllFolder();
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
//取得所有文件夹
function getAllFolder() {
  var url = "/user/libary/folder";
  $.ajax({
    url: url,
    type: "GET",
    dataType: "json",
    success: function (json) {
      $("#n-tbody").empty();

      var mainFolder =
        "<tr>" +
        "<th scope='row'/>" +
        "<td onclick='window.location.replace(&apos;/user/album&apos;)' style='font-size: large; color: lightcoral;'><a href='./album'>图库</a></td>" +
        "</tr>";
      $("#n-tbody").append(mainFolder);

      var list = json.data;
      list.forEach((element) => {
        var html =
          "<tr>" +
          "<th scope='row'>" +
          "<input type='checkbox' style='cursor: pointer' value='#{id}' onchange='checkboxValueChange(this)'/>" +
          "</th>" +
          "<td onclick='window.location.replace(&apos;/user/folder/page?id=#{id}&name=#{name}&apos;)'><a>#{name}</a></td>" +
          "</tr>";

        html = html.replace(/#{id}/g, element.id);
        html = html.replace(/#{name}/g, element.name);
        $("#n-tbody").append(html);
      });
    },
  });
}
//发送到按钮
function send() {
  let folderIds = new Array();
  $("#n-tbody input[type=checkbox]").each(function () {
    if ($(this).prop("checked") == true) {
      folderIds.push($(this).val());
    }
  });
  let groupIds = new Array();
  $("#n-sendmodal-tbody input[name='group']").each(function () {
    if ($(this).prop("checked") == true) {
      groupIds.push($(this).val());
    }
  });
  let customerIds = new Array();
  $("#n-sendmodal-tbody input[name='customer']").each(function () {
    if ($(this).prop("checked") == true) {
      customerIds.push($(this).val());
    }
  });
  if (groupIds.length > 0 || customerIds.length > 0) {
    var url = "/user/send/sendFolder";

    $.ajax({
      url: url,
      data: {
        groupIds: JSON.stringify(groupIds),
        customerIds: JSON.stringify(customerIds),
        folderIds: folderIds,
      },
      type: "PUT",
      dataType: "json",
      success: function (json) {
        swal(
          {
            title: "发送成功!",
            type: "success",
            button: "确认",
          },
          function () {
            window.location.reload();
          }
        );
      },
    });
  } else {
    swal("发送失败!", "请选择要发送到的客户!", "error", { button: "确认" });
  }
}
function triggerTick(td) {
  var $checkbox = $(td).find("input");
  $checkbox.prop("checked", !$checkbox.prop("checked"));
}
function checkboxTriggerTick(checkbox) {
  $(checkbox).prop("checked", !$(checkbox).prop("checked"));
}
//toggle 复制到按钮
function sendModalBtn(isSearch) {
  if (isSearch == undefined) {
    $("#n-customer").val("");
  }
  let folderIds = new Array();
  $("#n-tbody input[type=checkbox]").each(function () {
    if ($(this).prop("checked") == true) {
      folderIds.push($(this).val());
    }
  });

  $("#n-sendmodal-tbody").empty();
  if (folderIds.length > 0) {
    var url = "/user/customer/allgroup";
    $.ajax({
      url: url,
      data: { group: $("#n-customer").val() },
      type: "GET",
      dataType: "json",
      success: function (json) {
        var list = json.data;
        list.forEach((element) => {
          var html =
            "<tr onclick='triggerTick(this)'><td scope='row' >组 #{name}</td>" +
            "<td> " +
            "<input name='group' type='checkbox' class='float-end' style='cursor: pointer' value='#{id}' onclick='checkboxTriggerTick(this)' />" +
            "</td> " +
            "</tr>";

          html = html.replace(/#{id}/g, element.id);
          html = html.replace("#{name}", element.name);
          $("#n-sendmodal-tbody").append(html);
        });
        url = "/user/customer/allcustomer";
        $.ajax({
          url: url,
          data: { customer: $("#n-customer").val() },
          type: "GET",
          dataType: "json",
          success: function (json) {
            var list = json.data;
            list.forEach((element) => {
              var html =
                "<tr onclick='triggerTick(this)'><td scope='row' >#{name} &nbsp; #{nickname}</td>" +
                "<td> " +
                "<input name='customer'  type='checkbox' class='float-end' style='cursor: pointer' value='#{id}' onclick='checkboxTriggerTick(this)' />" +
                "</td> " +
                "</tr>";

              html = html.replace(/#{id}/g, element.id);
              html = html.replace("#{name}", element.name);
              html = html.replace("#{nickname}", element.nickname);
              $("#n-sendmodal-tbody").append(html);
            });
          },
        });
        $("#SendModal").modal("show");
      },
    });
  } else {
    swal("发送失败!", "请选择要发送项目!", "error", { button: "确认" });
  }
}
//全选及反选
$("#checkAll").click(function () {
  if ($(this).prop("checked") == true) {
    $("#n-tbody input[type=checkbox]").each(function () {
      $(this).prop("checked", true);
    });
  } else {
    $("#n-tbody input[type=checkbox]").each(function () {
      $(this).prop("checked", false);
    });
  }
});
//创建文件夹
function createFolder() {
  var foldername = {
    foldername: document.getElementById("n-createModalInput-folderName").value,
  };
  if (foldername != "") {
    $.ajax({
      url: "/user/libary/folder",
      type: "PUT",
      data: foldername,
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          window.location.replace("/user/libary");
        } else {
          swal("创建失败!", json.message, "error", { button: "确认" });
        }
      },
    });
  }
}
//新增按钮
function createBtn() {
  document.getElementById("n-createModalInput-folderName").value = "";
  init();
}
//重命名文件夹
function renameFolder() {
  var checkedFolderId;
  $("#n-tbody input[type=checkbox]").each(function () {
    if ($(this).prop("checked") == true) {
      checkedFolderId = $(this).val();
      return false;
    }
  });

  var folderInfo = {
    foldername: document.getElementById("n-renameModalInput-folderName").value,
    folderId: checkedFolderId,
  };
  if ((folderInfo.foldername != "") & (folderInfo.folderId != "")) {
    $.ajax({
      url: "/user/libary/folder",
      type: "PATCH",
      data: folderInfo,
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          window.location.replace("/user/libary");
        } else {
          swal("重命名失败!", json.message, "error", { button: "确认" });
        }
      },
    });
  }
}
//新增按钮
function renameBtn() {
  document.getElementById("n-renameModalInput-folderName").value = "";
}
//删除按钮
function delBtn() {
  var folderIds = new Array();
  $("#n-tbody input[type=checkbox]").each(function () {
    if ($(this).prop("checked") == true) {
      folderIds.push($(this).val());
    }
  });
  if (folderIds.length > 0) {
    $.ajax({
      url: "/user/libary/folder",
      type: "DELETE",
      data: { folderIds: folderIds },
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          swal(
            {
              title: "成功删除文件夹!",
              type: "success",
              button: "确认",
            },
            function () {
              window.location.reload();
            }
          );
        } else {
          swal("删除失败!", json.message, "error", { button: "确认" });
        }
      },
    });
  }
}

/**
 *根据选择个数able btns
 */
var countChecked = 0;

function checkboxValueChange(item) {
  //算chcked数量
  if (item.checked == true) countChecked += 1;
  else countChecked -= 1;

  changeButton();
}
function checkAllValueChange(item) {
  countChecked = 0;
  //算chcked数量
  if (item.checked == true) {
    $("#n-tbody input[type=checkbox]").each(function () {
      countChecked += 1;
    });
    countChecked -= 1;
  }
  changeButton();
}
function changeButton() {
  //根据数量对button做操作
  if (countChecked <= 0) {
    var elements = document.getElementsByClassName("n-function-btn");
    Array.prototype.forEach.call(elements, function (element) {
      element.disabled = "True";
    });
  } else if (countChecked == 1) {
    var elements = document.getElementsByClassName("n-function-btn");
    Array.prototype.forEach.call(elements, function (element) {
      element.disabled = "";
    });
  } else {
    document.getElementById("n-btn-send").disabled = "";
    document.getElementById("n-btn-delete").disabled = "";
    document.getElementById("n-btn-rename").disabled = "True";
  }
}

/**
 * 初始化
 */
function init() {
  countChecked = 0;
  var elements = document.getElementsByClassName("n-function-btn");
  Array.prototype.forEach.call(elements, function (element) {
    element.disabled = "True";
  });
  $("#n-tbody input[type=checkbox]").each(function () {
    $(this).prop("checked", false);
  });
  $("#checkAll").prop("checked", false);
}
