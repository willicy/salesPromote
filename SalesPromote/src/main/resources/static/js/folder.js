var modalItem;
var folderId;
window.onload = function () {
  folderId = getUrlPara("id");

  //init();
  getAllItem();

  $("#breadcrumb-title").html(getUrlPara("name"));
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
function getUrlPara(name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
  var r = window.location.search.substr(1).match(reg);

  if (r != null) return decodeURIComponent(r[2]);
  return null;
}
function getAllItem() {
  var url = "/user/folder/item";
  $.ajax({
    url: url,
    type: "GET",
    data: { id: folderId, itemName: $("#n-itemName").val() },
    dataType: "json",
    success: function (json) {
      $("#n-album").empty();
      var list = json.data;
      list.forEach((element) => {
        var html =
          "<div class='n-photoframe' name='photoframe' value='#{id}' onclick='modifyModalBtn(this)' data-bs-toggle = 'modal' data-bs-target='#ItemModal'>" +
          " <div class='n-photo'><img src='#{photoLocation}' class='img' alt='...' /></div>" +
          "<div class='n-photoname'><a>#{name}</a></div>" +
          "<div class='n-phototick'><img src='/images/common/tick.png' class='n-phototick-img' /></div></div>";
        html = html.replace("#{id}", element.id);
        html = html.replace("#{name}", element.name);
        html = html.replace("#{photoLocation}", element.photoLocation);
        $("#n-album").append(html);
      });
    },
  });
}

//发送到按钮
function send() {
  let itemIds = getTicked();

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
    var url = "/user/send/send";

    $.ajax({
      url: url,
      data: {
        groupIds: JSON.stringify(groupIds),
        customerIds: JSON.stringify(customerIds),
        itemIds: itemIds,
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
//toggle 复制到按钮
function sendModalBtn(isSearch) {
  let itemIds = getTicked();
  if (isSearch == undefined) {
    $("#n-customer").val("");
  }
  $("#n-sendmodal-tbody").empty();
  if (itemIds.length > 0) {
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

//复制到按钮
function copyTo() {
  let itemIds = getTicked();

  let folderIds = new Array();
  $("#n-foldermodal-tbody input[type=checkbox]").each(function () {
    if ($(this).prop("checked") == true) {
      folderIds.push($(this).val());
    }
  });
  if (folderIds.length > 0) {
    var url = "/user/folder/copyItemToFolders";

    $.ajax({
      url: url,
      data: {
        folderIds: JSON.stringify(folderIds),
        itemIds: JSON.stringify(itemIds),
      },
      type: "post",
      dataType: "json",
      success: function (json) {
        swal(
          {
            title: "复制成功!",
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
    swal("复制失败!", "请选择要复制到的文件夹!", "error", { button: "确认" });
  }
}
//toggle 复制到按钮
function copytoModalBtn() {
  let itemIds = getTicked();

  if (itemIds.length > 0) {
    var url = "/user/folder/folder";
    $.ajax({
      url: url,
      data: { id: folderId },
      type: "GET",
      dataType: "json",
      success: function (json) {
        $("#n-foldermodal-tbody").empty();
        var list = json.data;
        list.forEach((element) => {
          var html =
            "<tr onclick='triggerTick(this)'><td scope='row' >#{name}</td>" +
            "<td> " +
            "<input  type='checkbox' class='float-end' style='cursor: pointer' value='#{id}' onclick='checkboxTriggerTick(this)' />" +
            "</td> " +
            "</tr>";

          html = html.replace(/#{id}/g, element.id);
          html = html.replace("#{name}", element.name);
          $("#n-foldermodal-tbody").append(html);
        });
        $("#FolderModalLabel").html("复制到");
        $("#n-foldermodal-movetobtn").addClass("d-none");
        $("#n-foldermodal-copytobtn").removeClass("d-none");
      },
    });
    $("#FolderModal").modal("show");
  } else {
    swal("复制失败!", "请选择要复制项目!", "error", { button: "确认" });
  }
}
//移动到按钮
function moveTo() {
  let itemIds = getTicked();

  let folderIds = new Array();
  $("#n-foldermodal-tbody input[type=checkbox]").each(function () {
    if ($(this).prop("checked") == true) {
      folderIds.push($(this).val());
    }
  });
  if (folderIds.length > 0) {
    var url = "/user/folder/moveItemToFolders";

    $.ajax({
      url: url,
      data: {
        currentFolderId: folderId,
        folderIds: JSON.stringify(folderIds),
        itemIds: JSON.stringify(itemIds),
      },
      type: "post",
      dataType: "json",
      success: function (json) {
        swal(
          {
            title: "移动成功!",
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
    swal("移动失败!", "请选择要移动到的文件夹!", "error", { button: "确认" });
  }
}
//toggle 移动到按钮
function movetoModalBtn() {
  let itemIds = getTicked();

  if (itemIds.length > 0) {
    var url = "/user/folder/folder";
    $.ajax({
      url: url,
      data: { id: folderId },
      type: "GET",
      dataType: "json",
      success: function (json) {
        $("#n-foldermodal-tbody").empty();
        var list = json.data;
        list.forEach((element) => {
          var html =
            "<tr onclick='triggerTick(this)'><td scope='row' >#{name}</td>" +
            "<td> " +
            "<input  type='checkbox' class='float-end' style='cursor: pointer' value='#{id}' onclick='checkboxTriggerTick(this)' />" +
            "</td> " +
            "</tr>";

          html = html.replace(/#{id}/g, element.id);
          html = html.replace("#{name}", element.name);
          $("#n-foldermodal-tbody").append(html);
        });

        $("#FolderModalLabel").html("移动到");
        $("#n-foldermodal-copytobtn").addClass("d-none");
        $("#n-foldermodal-movetobtn").removeClass("d-none");
      },
    });
    $("#FolderModal").modal("show");
  } else {
    swal("移动失败!", "请选择要移动项目!", "error", { button: "确认" });
  }
}
//修改款式内容modal内的modify 按钮
function modifyItem() {
  //判断 有required的input 或 select是否有值
  let requiredVaidFlag = true;
  $(".required").each(function () {
    $(this).removeClass("is-invalid");
    if (
      $(this).next().attr("class") != undefined &&
      $(this).next().attr("class").indexOf("invalid-feedback") != -1
    ) {
      $(this).next().remove();
    }

    if ($(this).val() == "" || $(this).val() == "null") {
      if ($(this).attr("type") != "file") {
        requiredVaidFlag = false;
        $(this).addClass("is-invalid");
        $(this).after("<div class='invalid-feedback'>请填写!</div>");
      }
    }
  });
  if (requiredVaidFlag) {
    var data = $("#n-form-itemmodal").serialize();

    var url = "/user/album/modifyItem";
    var formData = new FormData($("#n-form-itemmodal")[0]);
    $.ajax({
      url: url,
      data: formData,

      async: false,
      cache: false,
      contentType: false,
      processData: false,
      type: "post",
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
}
//修改款式内容modal
function modifyModalBtn(value) {
  $("#n-modal-photoframe").empty();
  $("#n-form-itemmodal")[0].reset();
  $("#n-modal-createbtn").addClass("d-none");
  $("#n-modal-modifybtn").removeClass("d-none");
  $("#n-modal-inputSize").html("");
  $("#n-modal-inputType").html("");
  $(".required").each(function () {
    $(this).removeClass("is-invalid");
  });

  var promise1 = $.ajax({
    url: "/user/setting/size",
    type: "GET",
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        $("#n-modal-inputSize").append(
          "<option value='null' selected>请选择</option>"
        );
        const list = json.data;
        list.forEach((element) => {
          var html = "<option value='#{data}' >#{data}</option>";

          html = html.replace(/#{data}/g, element.data);
          $("#n-modal-inputSize").append(html);
        });
      } else {
        swal(
          {
            title: "开启失败!",
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
  var promise2 = $.ajax({
    url: "/user/setting/type",
    type: "GET",
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        $("#n-modal-inputType").append(
          "<option value='null' selected>请选择</option>"
        );
        const list = json.data;
        list.forEach((element) => {
          var html = "<option value='#{data}' >#{data}</option>";

          html = html.replace(/#{data}/g, element.data);
          $("#n-modal-inputType").append(html);
        });
      } else {
        swal(
          {
            title: "开启失败!",
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
  Promise.all([promise1, promise2]).then(function () {
    var itemId = $(value).attr("value");

    var url = "/user/album/item";
    $.ajax({
      url: url,
      data: { itemId: itemId },
      type: "GET",
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          modalItem = json.data;

          $("#ItemModalLabel").html("编辑 " + modalItem.name);

          $("#n-modal-photoframe").append(
            '<img src="' + modalItem.photoLocation + '" class="img-fluid" />'
          );
          $("#n-modal-inputUpdateTime").val(modalItem.updateTime);
          $("#n-modal-inputId").val(modalItem.id);
          $("#n-modal-inputName").val(modalItem.name);
          $("#n-modal-inputColor").val(modalItem.color);
          $("#n-modal-inputType").val(modalItem.type);
          $("#n-modal-inputSize").val(modalItem.size);
          $("#n-modal-inputPrice1").val(modalItem.price1);
          $("#n-modal-inputPrice2").val(modalItem.price2);
          $("#n-modal-inputPrice3").val(modalItem.price3);
          $("#n-modal-inputPrice4").val(modalItem.price4);
          $("#n-modal-inputState").val(modalItem.state);
          $("#n-modal-inputRemark").val(modalItem.remark);
          if ($("#n-modal-inputSize").val() == null) {
            $("#n-modal-inputSize").append(
              '<option value="" disabled selected hidden>' +
                modalItem.size +
                "</option>"
            );
          }
          if ($("#n-modal-inputType").val() == null) {
            $("#n-modal-inputType").append(
              '<option value="" disabled selected hidden>' +
                modalItem.type +
                "</option>"
            );
          }
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
  });
}
//相片打勾ui
function tickPhoto(photoframe) {
  $(photoframe).toggleClass("n-photoframe-select-ticked");
  $(photoframe).toggleClass("n-photoframe-select");
}

//选择按钮
function selectBtn() {
  //modal 内容

  //相片ui
  $.each($("[name='photoframe']"), function () {
    $(this).removeClass("n-photoframe");
    $(this).addClass("n-photoframe-select");
    $(this).attr("onclick", "tickPhoto(this)");
    $(this).attr("data-bs-toggle", "");
    $(this).attr("data-bs-target", "");
  });

  //按钮ui
  $("#n-btn-select").addClass("d-none");
  $("#n-btn-create").addClass("d-none");

  $("#n-btn-send").removeClass("d-none");
  $("#n-btn-delete").removeClass("d-none");
  $("#n-btn-copyTo").removeClass("d-none");
  $("#n-btn-moveTo").removeClass("d-none");
  $("#n-btn-unselect").removeClass("d-none");
}
//create modal 里的照片预览功能
function filePreview(input) {
  if (input.files && input.files[0]) {
    var reader = new FileReader();
    reader.onload = function (e) {
      $("#n-modal-photoframe").empty();
      $("#n-modal-photoframe").append(
        '<img src="' + e.target.result + '" class="img-fluid" />'
      );
    };
    reader.readAsDataURL(input.files[0]);
  }
}
$("#n-modal-file").change(function () {
  filePreview(this);
});
//删除按钮
function delBtn() {
  let itemIds = getTicked();

  if (itemIds.length > 0) {
    $.ajax({
      url: "/user/folder/item",
      type: "DELETE",
      data: { itemIds: itemIds, folderId: folderId },
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          swal(
            {
              title: "删除成功!",
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
    swal("删除失败", "请选择要删除项目!", "error", { button: "确认" });
  }
}

//获取选择相片
function getTicked() {
  let ticked_Photo = new Array();
  $.each($("[name='photoframe']"), function () {
    $(this).attr("class").indexOf("n-photoframe-select-ticked") != -1;

    if ($(this).attr("class").indexOf("n-photoframe-select-ticked") != -1) {
      ticked_Photo.push($(this).attr("value"));
    }
  });
  return ticked_Photo;
}
//create modal 里的form submit
function createItem() {
  //判断 有required的input 或 select是否有值
  let requiredVaidFlag = true;
  $(".required").each(function () {
    $(this).removeClass("is-invalid");
    if (
      $(this).next().attr("class") != undefined &&
      $(this).next().attr("class").indexOf("invalid-feedback") != -1
    ) {
      $(this).next().remove();
    }

    if ($(this).val() == "" || $(this).val() == "null") {
      requiredVaidFlag = false;
      $(this).addClass("is-invalid");
      $(this).after("<div class='invalid-feedback'>请填写!</div>");
    }
  });

  //判断
  if (requiredVaidFlag) {
    var url = "/user/folder/item";

    var formData = new FormData($("#n-form-itemmodal")[0]);
    formData.append("folderId", folderId);

    // 发出ajax请求，并处理结果
    $.ajax({
      url: url,
      data: formData,
      type: "post",
      async: false,
      cache: false,
      contentType: false,
      processData: false,
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          swal(
            {
              title: "创建成功!",
              type: "success",
              button: "确认",
            },
            function () {
              window.location.reload();
            }
          );
        } else {
          swal("创建失败!", json.message, "error", { button: "确认" });
        }
      },
    });
  }
}
function triggerTick(td) {
  var $checkbox = $(td).find("input");
  $checkbox.prop("checked", !$checkbox.prop("checked"));
}
function checkboxTriggerTick(checkbox) {
  $(checkbox).prop("checked", !$(checkbox).prop("checked"));
}
function createModalBtn() {
  $("#n-modal-photoframe").empty();
  $("#n-form-itemmodal")[0].reset();
  $("#ItemModalLabel").html("新增款");
  $("#n-modal-createbtn").removeClass("d-none");
  $("#n-modal-modifybtn").addClass("d-none");
  $("#n-modal-inputSize").html("");
  $("#n-modal-inputType").html("");
  $(".required").each(function () {
    $(this).removeClass("is-invalid");
  });
  $.ajax({
    url: "/user/setting/size",
    type: "GET",
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        $("#n-modal-inputSize").append(
          "<option value='null' selected>请选择</option>"
        );
        const list = json.data;
        list.forEach((element) => {
          var html = "<option value='#{data}' >#{data}</option>";

          html = html.replace(/#{data}/g, element.data);
          $("#n-modal-inputSize").append(html);
        });
      } else {
        swal(
          {
            title: "开启失败!",
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
  $.ajax({
    url: "/user/setting/type",
    type: "GET",
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        $("#n-modal-inputType").append(
          "<option value='null' selected>请选择</option>"
        );
        const list = json.data;
        list.forEach((element) => {
          var html = "<option value='#{data}' >#{data}</option>";

          html = html.replace(/#{data}/g, element.data);
          $("#n-modal-inputType").append(html);
        });
      } else {
        swal(
          {
            title: "开启失败!",
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

//初始化
function init() {
  $("#n-btn-select").removeClass("d-none");
  $("#n-btn-create").removeClass("d-none");

  $("#n-btn-send").addClass("d-none");
  $("#n-btn-delete").addClass("d-none");
  $("#n-btn-copyTo").addClass("d-none");
  $("#n-btn-moveTo").addClass("d-none");
  $("#n-btn-unselect").addClass("d-none");
}
