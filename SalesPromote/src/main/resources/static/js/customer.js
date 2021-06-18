window.onload = function () {
  init();
  getAllCustomer();
  getAllGroup();
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
function getAllCustomer() {
  var url = "/user/customer/allcustomer";
  $.ajax({
    url: url,
    data: { customer: $("#n-customerName").val() },
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
          "<td onclick='modifyModalBtn(this)' value='#{id}' data-bs-toggle = 'modal' data-bs-target='#CustomerModal'><a>#{name}</a></td>" +
          "<td onclick='modifyModalBtn(this)' value='#{id}' data-bs-toggle = 'modal' data-bs-target='#CustomerModal'><a>#{nickname}</a></td>" +
          "<td onclick='modifyModalBtn(this)' value='#{id}' data-bs-toggle = 'modal' data-bs-target='#CustomerModal'><a>#{country}</a></td>" +
          "<td onclick='modifyModalBtn(this)' value='#{id}' data-bs-toggle = 'modal' data-bs-target='#CustomerModal'><a>#{district}</a></td>" +
          "</tr>";

        html = html.replace(/#{id}/g, element.id);
        html = html.replace("#{name}", element.name);
        html = html.replace("#{nickname}", element.nickname);
        html = html.replace("#{district}", element.district);
        html = html.replace("#{country}", element.country);
        $("#n-tbody").append(html);
      });
    },
  });
}

//修改款式内容modal内的modify 按钮
function modifyCustomer() {
  //判断 有required的input 或 select是否有值
  if ($("#n-modal-inputName").val() == "") {
    swal("请填写用户名", "", "error", { button: "确认" });
    return;
  }
  //判断
  if ($("#n-modal-inputCode").val() == "") {
    swal("请填写账号", "", "error", { button: "确认" });
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
  var formdata = $("#n-form-customermodal").serialize();

  var url = "/user/customer/customer";
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
  $("#n-form-customermodal")[0].reset();

  $("#n-modal-createbtn").addClass("d-none");
  $("#n-modal-modifybtn").removeClass("d-none");
  var customerId = $(value).attr("value");

  var url = "/user/customer/customer";
  $.ajax({
    url: url,
    data: { customerId: customerId },
    type: "GET",
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        modalCustomer = json.data;

        $("#CustomerModalLabel").html("编辑 " + modalCustomer.name);
        $("#n-modal-inputName").val(modalCustomer.name);
        $("#n-modal-inputNickname").val(modalCustomer.nickname);
        $("#n-modal-inputPhone").val(modalCustomer.phone);
        $("#n-modal-inputPassword").val(modalCustomer.password);
        $("#n-modal-inputCountry").val(modalCustomer.country);
        $("#n-modal-inputDistrict").val(modalCustomer.district);
        $("#n-modal-inputId").val(modalCustomer.id);
        $("#n-modal-inputSalesId").val(modalCustomer.salesId);
        $("#n-modal-inputCode").val(modalCustomer.code);
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

//创建客户
function createCustomer() {
  //判断 有required的input 或 select是否有值
  if ($("#n-modal-inputName").val() == "") {
    swal("请填写客户名", "", "error", { button: "确认" });
    return;
  }
  if ($("#n-modal-inputCode").val() == "") {
    swal("请填写账号", "", "error", { button: "确认" });
    return;
  }
  if (
    !/^(?=.*[a-zA-Z])(?=.*\d)[A-Za-z\d]{8,16}$/.test(
      document.getElementById("n-modal-inputPassword").value
    )
  ) {
    swal(
      "新增失败",
      "密码至少8个字符，至少1个字母和1个数字,不能包含特殊字符，最大长度为16位。",

      "error",
      { button: "确认" }
    );
    return;
  }
  var formdata = $("#n-form-customermodal").serialize();

  $.ajax({
    url: "/user/customer/customer",
    type: "PUT",
    data: formdata,
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        swal(
          {
            title: "成功新增客户!",
            type: "success",
            button: "确认",
          },
          function () {
            window.location.reload();
          }
        );
      } else {
        swal("新增失败!", json.message, "error", { button: "确认" });
      }
    },
  });
}
//新增按钮
function createBtn() {
  $("#n-form-customermodal")[0].reset();
  $("#n-modal-createbtn").removeClass("d-none");
  $("#n-modal-modifybtn").addClass("d-none");
  $("#CustomerModalLabel").html("新增客户");
}

//删除按钮
function delBtn() {
  var customerIds = new Array();
  $("#n-tbody input[type=checkbox]").each(function () {
    if ($(this).prop("checked") == true) {
      customerIds.push($(this).val());
    }
  });
  if (customerIds.length > 0) {
    $.ajax({
      url: "/user/customer/customer",
      type: "DELETE",
      data: { customerIds: customerIds },
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          swal(
            {
              title: "成功删除客户资料!",
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
  } else {
    swal("删除失败!", "请选择要删除客户", "error", { button: "确认" });
  }
}
/*                              组                          */
//取得所有客户
function getAllGroup() {
  var url = "/user/customer/allgroup";
  $.ajax({
    url: url,
    data: { group: $("#n-groupName").val() },
    type: "GET",
    dataType: "json",
    success: function (json) {
      $("#n-tbody-group").empty();

      var list = json.data;
      list.forEach((element) => {
        var html =
          "<tr>" +
          "<th scope='row'>" +
          "<input type='checkbox' style='cursor: pointer' value='#{id}' />" +
          "</th>" +
          "<td onclick='modifyGroupModalBtn(#{id})'  data-bs-toggle = 'modal' data-bs-target='#GroupModal'><a>#{name}</a></td>" +
          "</tr>";

        html = html.replace(/#{id}/g, element.id);
        html = html.replace("#{name}", element.name);
        $("#n-tbody-group").append(html);
      });
    },
  });
}
//修改组内容modal内的modify 按钮
function modifyGroup() {
  //判断 有required的input 或 select是否有值
  if ($("#n-groupmodal-inputName").val() == "") {
    swal("请填写组名", "", "error", { button: "确认" });
    return;
  }
  var formdata = $("#n-form-groupmodal").serialize();
  var url = "/user/customer/group";
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
            $("#GroupModal").modal("hide");
            getAllGroup();
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
//修改组内容modal
function modifyGroupModalBtn(groupId) {
  $("#n-form-groupmodal")[0].reset();
  $("#n-groupmodal-customertable").removeClass("d-none");
  $("#n-groupmodal-createbtn").addClass("d-none");
  $("#n-groupmodal-modifybtn").removeClass("d-none");

  var url = "/user/customer/group";
  $.ajax({
    url: url,
    data: { groupId: groupId },
    type: "GET",
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        modalGroup = json.data;
        $("#GroupModalLabel").html("编辑 " + modalGroup.name);
        $("#n-groupmodal-inputName").val(modalGroup.name);
        $("#n-groupmodal-inputId").val(modalGroup.id);
        $("#n-groupmodal-inputSalesId").val(modalGroup.salesId);
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
  url = "/user/customer/allcustomergroup";
  $.ajax({
    url: url,
    data: { groupId: groupId },
    type: "GET",
    dataType: "json",
    success: function (json) {
      $("#n-tbody-groupmodal").empty();

      var list = json.data;
      list.forEach((element) => {
        var html =
          "<tr>" +
          "<td scope='row'><a>#{name}</a></td>" +
          "<td><a>#{nickname}</a></td>" +
          "<td><a>#{country}</a></td>" +
          "<td><a>#{district}</a></td>" +
          "<td style='width: 15%'>" +
          "<button type='button ' onclick='delCustomerGroupBtn(#{id})' class='btn btn-sm btn-danger' >X</button>" +
          "</td>" +
          "</tr>";

        html = html.replace(/#{id}/g, element.id);
        html = html.replace("#{name}", element.name);
        html = html.replace("#{nickname}", element.nickname);
        html = html.replace("#{district}", element.district);
        html = html.replace("#{country}", element.country);
        $("#n-tbody-groupmodal").append(html);
      });
    },
  });
}
//创建组
function createGroup() {
  //判断 有required的input 或 select是否有值
  if ($("#n-groupmodal-inputName").val() == "") {
    swal("请填写组名", "", "error", { button: "确认" });
    return;
  }

  $.ajax({
    url: "/user/customer/group",
    type: "PUT",
    data: { name: $("#n-groupmodal-inputName").val() },
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        swal(
          {
            title: "成功新增组!",
            type: "success",
            button: "确认",
          },
          function () {
            $("#GroupModal").modal("hide");
            getAllGroup();
          }
        );
      } else {
        swal("新增组失败!", json.message, "error", { button: "确认" });
      }
    },
  });
}
//新增按钮
function createGroupBtn() {
  $("#n-groupmodal-customertable").addClass("d-none");
  $("#n-form-groupmodal")[0].reset();
  $("#n-groupmodal-createbtn").removeClass("d-none");
  $("#n-groupmodal-modifybtn").addClass("d-none");
  $("#GroupModalLabel").html("新增组");
}
//删除按钮
function delGroupBtn() {
  var groupIds = new Array();
  $("#n-tbody-group input[type=checkbox]").each(function () {
    if ($(this).prop("checked") == true) {
      groupIds.push($(this).val());
    }
  });
  if (groupIds.length > 0) {
    $.ajax({
      url: "/user/customer/group",
      type: "DELETE",
      data: { groupIds: groupIds },
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          swal(
            {
              title: "成功删除组资料!",
              type: "success",
              button: "确认",
            },
            function () {
              $("#GroupModal").modal("hide");
              getAllGroup();
            }
          );
        } else {
          swal("删除失败!", json.message, "error", { button: "确认" });
        }
      },
    });
  } else {
    swal("删除失败!", "请选择要删除组", "error", { button: "确认" });
  }
}
/*                              组    客户                      */

//新增组内客户
function createGroupCustomer() {
  var customerIds = new Array();
  $("#n-tbody-customergroupmodal input[type=checkbox]").each(function () {
    if ($(this).prop("checked") == true) {
      customerIds.push($(this).val());
    }
  });
  if (customerIds.length > 0) {
    $.ajax({
      url: "/user/customer/customerGroup",
      type: "PUT",
      data: {
        customerIds: customerIds,
        groupId: $("#n-groupmodal-inputId").val(),
      },
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          modifyGroupModalBtn($("#n-groupmodal-inputId").val());
          $("#closeGroupCustomerModal").click();
        } else {
          swal("新增失败!", json.message, "error", { button: "确认" });
        }
      },
    });
  } else {
    swal("新增失败!", "请选择要新增客户", "error", { button: "确认" });
  }
}
//新增按钮
function createCustomerGroupBtn(isSearch) {
  if (isSearch == undefined) {
    $("#n-customer").val("");
  }
  var url = "/user/customer/allcustomergroupcustomer";
  $.ajax({
    url: url,
    data: {
      customer: $("#n-customer").val(),
      groupId: $("#n-groupmodal-inputId").val(),
    },
    type: "GET",
    dataType: "json",
    success: function (json) {
      $("#n-tbody-customergroupmodal").empty();

      var list = json.data;
      list.forEach((element) => {
        var html =
          "<tr onclick='triggerTick(this)'>" +
          "<th scope='row'>" +
          "<input type='checkbox' style='cursor: pointer' value='#{id}' onclick='checkboxTriggerTick(this)'/>" +
          "</th>" +
          "<td ><a>#{name}</a></td>" +
          "<td ><a>#{nickname}</a></td>" +
          "<td ><a>#{country}</a></td>" +
          "<td ><a>#{district}</a></td>" +
          "</tr>";

        html = html.replace(/#{id}/g, element.id);
        html = html.replace("#{name}", element.name);
        html = html.replace("#{nickname}", element.nickname);
        html = html.replace("#{district}", element.district);
        html = html.replace("#{country}", element.country);
        $("#n-tbody-customergroupmodal").append(html);
      });
    },
  });
}
//删除按钮
function delCustomerGroupBtn(customerId) {
  $.ajax({
    url: "/user/customer/customerGroup",
    type: "DELETE",
    data: { groupId: $("#n-groupmodal-inputId").val(), customerId: customerId },
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        modifyGroupModalBtn($("#n-groupmodal-inputId").val());
      } else {
        swal("删除失败!", json.message, "error", { button: "确认" });
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
