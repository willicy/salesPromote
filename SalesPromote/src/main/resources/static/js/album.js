var modalItem;

window.onload = function () {
  init();
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
//取得所有款
function getAllItem() {
  var url = "/user/album/allitem";
  $.ajax({
    url: url,
    data: { itemName: $("#n-itemName").val() },
    type: "GET",
    dataType: "json",
    success: function (json) {
      $("#n-album").empty();
      var list = json.data;
      list.forEach((element) => {
        var html =
          "<div class='n-photoframe' name='photoframe' value='#{id}' onclick='modifyModalBtn(this)' >" +
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
              console.log(element);
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
  $("#n-copyto-tbody input[type=checkbox]").each(function () {
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
    var url = "/user/libary/folder";
    $.ajax({
      url: url,
      type: "GET",
      dataType: "json",
      success: function (json) {
        $("#n-copyto-tbody").empty();
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
          $("#n-copyto-tbody").append(html);
        });
      },
    });
    $("#FolderModal").modal("show");
  } else {
    swal("复制失败!", "请选择要复制项目!", "error", { button: "确认" });
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
    var customer = new FormData($("#n-form-itemmodal")[0]);
    $.ajax({
      url: url,
      data: customer,

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
          console.log(json);
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
  $("#detailPhotoBtn").removeClass("d-none");
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
  var itemId = $(value).attr("value");
  Promise.all([promise1, promise2]).then(function () {
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
  $("#n-btn-unselect").removeClass("d-none");
}
//删除按钮
function delBtn() {
  let itemIds = getTicked();

  if (itemIds.length > 0) {
    $.ajax({
      url: "/user/album/item",
      type: "DELETE",
      data: { itemIds: itemIds },
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
    var url = "/user/album/item";
    // 请求参数
    var data = $("#n-form-itemmodal").serialize();
    var formData = new FormData($("#n-form-itemmodal")[0]);

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
      error: function (jqXHR, textStatus, errorThrown) {
        /*弹出jqXHR对象的信息*/
        alert(jqXHR.responseText);
        alert(jqXHR.status);
        alert(jqXHR.readyState);
        alert(jqXHR.statusText);
        /*弹出其他两个参数的信息*/
        alert(textStatus);
        alert(errorThrown);
      },
    });
  }
}

function createModalBtn() {
  $("#n-modal-photoframe").empty();
  $("#n-form-itemmodal")[0].reset();
  $("#ItemModalLabel").html("新增款");
  $("#n-modal-createbtn").removeClass("d-none");
  $("#n-modal-modifybtn").addClass("d-none");
  $("#n-modal-inputSize").html("");
  $("#n-modal-inputType").html("");
  $("#detailPhotoBtn").addClass("d-none");
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
  $("#ItemModal").modal("show");
}
function modifyItemPhoto() {
  var data = new FormData($("#itemPhotoForm")[0]);

  var removePhotos = new Array();
  var flag;
  for (var i = 1; i <= 5; i++) {
    flag = 1;

    $("#itemPhotoForm img").each(function () {
      if (i == $(this).attr("value")) {
        flag = 0;
      }
    });
    if (flag == 1) {
      removePhotos.push(i);
    }
  }

  data.append("removePhotos", removePhotos);
  data.append("id", $("#n-modal-inputId").val());
  $.ajax({
    url: "/user/album/itemphoto",
    data: data,

    type: "POST",
    async: false,
    cache: false,
    contentType: false,
    processData: false,
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        swal(
          {
            title: "保存成功!",
            type: "success",
            button: "确认",
          },
          function () {
            $("#ItemPhotoModal").modal("hide");
          }
        );
      } else {
        swal({
          title: "保存失败!",
          text: json.message,
          type: "error",
          button: "确认",
        });
      }
    },
  });
}
function itemPhotoModalBtn() {
  $("#n-ItemPhotomodal-photoframe1").empty();
  $("#n-ItemPhotomodal-photoframe2").empty();
  $("#n-ItemPhotomodal-photoframe3").empty();
  $("#n-ItemPhotomodal-photoframe4").empty();
  $("#n-ItemPhotomodal-photoframe5").empty();

  $("#n-ItemPhotomodal-file1").val("");
  $("#n-ItemPhotomodal-file2").val("");
  $("#n-ItemPhotomodal-file3").val("");
  $("#n-ItemPhotomodal-file4").val("");
  $("#n-ItemPhotomodal-file5").val("");
  $.ajax({
    url: "/user/album/allitemphoto",
    data: { id: $("#n-modal-inputId").val() },
    type: "GET",
    dataType: "json",
    success: function (json) {
      if (json.state == 200) {
        var photos = json.data;

        photos.forEach(function (data) {
          $("#n-ItemPhotomodal-photoframe" + data.priority).append(
            '<img value="' +
              data.priority +
              '" src="' +
              data.photoLocation +
              '" class="img" />'
          );
        });
      }
    },
  });
  $("#ItemPhotoModal").modal("show");
}
function deleteItemPhoto(index) {
  $("#n-ItemPhotomodal-file" + index).val("");
  $("#n-ItemPhotomodal-photoframe" + index).empty();
}
function filePreviewItemPhoto(input, index) {
  if (input.files && input.files[0]) {
    var reader = new FileReader();
    reader.onload = function (e) {
      $("#n-ItemPhotomodal-photoframe" + index).empty();
      $("#n-ItemPhotomodal-photoframe" + index).append(
        '<img value="' +
          index +
          '" src="' +
          e.target.result +
          '" class="img" />'
      );
    };
    reader.readAsDataURL(input.files[0]);
  }
}
$("#n-modal-file").change(function () {
  filePreview(this);
});
function triggerTick(td) {
  var $checkbox = $(td).find("input");
  $checkbox.prop("checked", !$checkbox.prop("checked"));
}
function checkboxTriggerTick(checkbox) {
  $(checkbox).prop("checked", !$(checkbox).prop("checked"));
}
//初始化
function init() {
  $("#n-btn-select").removeClass("d-none");
  $("#n-btn-create").removeClass("d-none");

  $("#n-btn-send").addClass("d-none");
  $("#n-btn-delete").addClass("d-none");
  $("#n-btn-copyTo").addClass("d-none");
  $("#n-btn-unselect").addClass("d-none");
}
