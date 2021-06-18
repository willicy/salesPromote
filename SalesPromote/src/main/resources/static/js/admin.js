window.onload = function () {
  init();
  getAllUser();
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
/*                              客户                          */
//取得所有客户
function getAllUser() {
  var url = "/admin/alluser";
  $.ajax({
    url: url,
    type: "GET",
    dataType: "json",
    success: function (json) {
      $("#n-tbody").empty();

      var list = json.data;
      list.forEach((element) => {
        var html =
          "<tr>" +
          "<th scope='row'>" +
          "<input type='checkbox' style='cursor: pointer' value='#{id}'/>" +
          "</th>" +
          "<td onclick='modifyModalBtn(this)' value='#{id}' data-bs-toggle = 'modal' data-bs-target='#userModal'><a>#{name}</a></td>" +
          "</tr>";

        html = html.replace(/#{name}/g, element.username);
        html = html.replace(/#{id}/g, element.id);
        $("#n-tbody").append(html);
      });
    },
  });
}

//修改款式内容modal内的modify 按钮
function modifyUser() {
  //判断 有required的input 或 select是否有值
  if ($("#n-modal-inputName").val() == "") {
    swal("请填写用户账号", "", "error", { button: "确认" });
    return;
  }

  if (
    !/^(?=.*[a-zA-Z])(?=.*\d)[A-Za-z\d]{8,16}$/.test(
      document.getElementById("n-modal-inputPassword").value
    )
  ) {
    swal(
      "更新失败",
      "密码至少8个字符，至少1个字母和1个数字,不能包含特殊字符，最大长度为16位。",

      "error",
      { button: "确认" }
    );
    return;
  }
  var formdata = $("#n-form-userModal").serialize();
  var url = "/admin/user";
  $.ajax({
    url: url,
    data: formdata,
    type: "patch",
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        swal(
          {
            title: "成功更新资料!",
            type: "success",
            button: "确认",
          },
          function () {
            window.location.reload();
          }
        );
      } else {
        swal({
          title: "更新资料失败!",
          text: json.message,
          type: "error",
          button: "确认",
        });
      }
    },
  });
}
//修改款式内容modal
function modifyModalBtn(value) {
  $("#n-form-userModal")[0].reset();

  var userId = $(value).attr("value");

  var url = "/admin/user";
  $.ajax({
    url: url,
    data: { userId: userId },
    type: "GET",
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        modal = json.data;
        $("#userModalLabel").html("编辑 " + modal.username);
        $("#n-modal-inputName").val(modal.username);
        $("#n-modal-inputPassword").val(modal.password);
        $("#n-modal-inputId").val(modal.id);
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

//删除按钮
function delBtn() {
  var userIds = new Array();
  $("#n-tbody input[type=checkbox]").each(function () {
    if ($(this).prop("checked") == true) {
      userIds.push($(this).val());
    }
  });
  if (userIds.length > 0) {
    $.ajax({
      url: "/admin/user",
      type: "DELETE",
      data: { userIds: userIds },
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          swal({
            title: "成功删除资料!",
            type: "success",
            button: "确认",
          }).then(() => {
            window.location.reload();
          });
        } else {
          swal("删除失败!", json.message, "error", { button: "确认" });
        }
      },
    });
  } else {
    swal("删除失败!", "请选择要删除用户", "error", { button: "确认" });
  }
}
function logoutBtn() {
  //后端验证

  var url = "/admin/logout";
  // 请求参数

  // 发出ajax请求，并处理结果
  $.ajax({
    url: url,
    type: "GET",
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        window.location.replace("/admin");
      } else {
        swal("失败!", json.message, "error", { button: "确认" });
      }
    },
  });
}
function triggerTick(td) {
  var $checkbox = $(td).find("input");
  $checkbox.prop("checked", !$checkbox.prop("checked"));
}
function checkboxTriggerTick(checkbox) {
  $(checkbox).prop("checked", !$(checkbox).prop("checked"));
}
/**
 * 初始化
 */
function init() {
  $("input[type=checkbox]").each(function () {
    $(this).prop("checked", false);
  });
}
