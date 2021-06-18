var customerId;
window.onload = function () {
  customerId = getUrlPara("id");

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
  var url = "/user/send/item";
  $.ajax({
    url: url,
    type: "GET",
    data: { id: customerId },
    dataType: "json",
    success: function (json) {
      $("#n-album").empty();
      var list = json.data;
      list.forEach((element) => {
        var html =
          "<div class='n-photoframe' name='photoframe' value='#{id}' >" +
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
//add item
function addCustomerItem() {
  let itemIds = getModalTicked();
  if (itemIds.length > 0) {
    $.ajax({
      url: "/user/customer/customerItem",
      type: "PUT",
      data: {
        customerId: customerId,
        itemIds: itemIds,
      },
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          swal(
            {
              title: "成功新增!",
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
  } else {
    swal("新增失败!", "请选择要新增款", "error", { button: "确认" });
  }
}
//toggle create modal
function createModalBtn() {
  $("#n-tickall").prop("checked", false);
  $("#n-modal-album").empty();
  //getdropdown
  var url = "/user/libary/folder";
  $.ajax({
    url: url,
    type: "GET",
    dataType: "json",
    success: function (json) {
      $("#modal-dropdown").empty();

      var mainFolder =
        "<option value=-1 selected>请选择文件夹</option>" +
        "<option value='main'>图库</option>";
      $("#modal-dropdown").append(mainFolder);

      var list = json.data;
      list.forEach((element) => {
        var html = "<option value='#{id}'>#{name}</option>";

        html = html.replace("#{id}", element.id);
        html = html.replace("#{name}", element.name);
        $("#modal-dropdown").append(html);
      });
    },
  });
}
//dropdown change 取modal 里的item
function getModalItem(folder) {
  $("#n-tickall").prop("checked", false);
  $("#n-modal-album").empty();
  if ($(folder).val() == -1) {
    return;
  }
  if ($(folder).val() == "main") {
    getAllAlbumItem();
  } else {
    getAllFolderItem($(folder).val());
  }
}

//modal 里的item
function getAllAlbumItem() {
  var url = "/user/album/allitem";
  $.ajax({
    url: url,
    type: "GET",
    dataType: "json",
    success: function (json) {
      $("#n-modal-album").empty();
      var list = json.data;
      list.forEach((element) => {
        var html =
          "<div class='n-photoframe-select' name='photoframe' value='#{id}' onclick='tickPhoto(this)'>" +
          " <div class='n-photo'><img src='#{photoLocation}' class='img' alt='...' /></div>" +
          "<div class='n-photoname'><a>#{name}</a></div>" +
          "<div class='n-phototick'><img src='/images/common/tick.png' class='n-phototick-img' /></div></div>";
        html = html.replace("#{id}", element.id);
        html = html.replace("#{name}", element.name);
        html = html.replace("#{photoLocation}", element.photoLocation);
        $("#n-modal-album").append(html);
      });
    },
  });
}
//modal 里的item
function getAllFolderItem(folderId) {
  var url = "/user/folder/item";
  $.ajax({
    url: url,
    type: "GET",
    data: { id: folderId },
    dataType: "json",
    success: function (json) {
      $("#n-modal-album").empty();
      var list = json.data;
      list.forEach((element) => {
        var html =
          "<div class='n-photoframe-select' name='photoframe' value='#{id}' onclick='tickPhoto(this)' >" +
          " <div class='n-photo'><img src='#{photoLocation}' class='img' alt='...' /></div>" +
          "<div class='n-photoname'><a>#{name}</a></div>" +
          "<div class='n-phototick'><img src='/images/common/tick.png' class='n-phototick-img' /></div></div>";
        html = html.replace("#{id}", element.id);
        html = html.replace("#{name}", element.name);
        html = html.replace("#{photoLocation}", element.photoLocation);
        $("#n-modal-album").append(html);
      });
    },
  });
}
//modal tickAll
function tickAll() {
  if ($("#n-tickall").prop("checked") == true) {
    $.each($("#n-modal-album [name='photoframe']"), function () {
      $(this).removeClass("n-photoframe-select");
      $(this).addClass("n-photoframe-select-ticked");
    });
  } else {
    $.each($("#n-modal-album [name='photoframe']"), function () {
      $(this).addClass("n-photoframe-select");
      $(this).removeClass("n-photoframe-select-ticked");
    });
  }
}
//相片打勾ui
function tickPhoto(photoframe) {
  $(photoframe).toggleClass("n-photoframe-select-ticked");
  $(photoframe).toggleClass("n-photoframe-select");
}
//获取选择相片
function getModalTicked() {
  let ticked_Photo = new Array();
  $.each($("#n-modal-album [name='photoframe']"), function () {
    $(this).attr("class").indexOf("n-photoframe-select-ticked") != -1;

    if ($(this).attr("class").indexOf("n-photoframe-select-ticked") != -1) {
      ticked_Photo.push($(this).attr("value"));
    }
  });
  return ticked_Photo;
}
//选择按钮
function selectBtn() {
  //相片ui
  $.each($("#n-album [name='photoframe']"), function () {
    $(this).removeClass("n-photoframe");
    $(this).addClass("n-photoframe-select");
    $(this).attr("onclick", "tickPhoto(this)");
    $(this).attr("data-bs-toggle", "");
    $(this).attr("data-bs-target", "");
  });

  //按钮ui
  $("#n-btn-select").addClass("d-none");
  $("#n-btn-create").addClass("d-none");

  $("#n-btn-delete").removeClass("d-none");
  $("#n-btn-unselect").removeClass("d-none");
}

//删除按钮
function delBtn() {
  let itemIds = getTicked();

  if (itemIds.length > 0) {
    $.ajax({
      url: "/user/customer/customerItem",
      type: "DELETE",
      data: { itemIds: itemIds, customerId: customerId },
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
  $.each($("#n-album [name='photoframe']"), function () {
    $(this).attr("class").indexOf("n-photoframe-select-ticked") != -1;

    if ($(this).attr("class").indexOf("n-photoframe-select-ticked") != -1) {
      ticked_Photo.push($(this).attr("value"));
    }
  });
  return ticked_Photo;
}

//初始化
function init() {
  $("#n-btn-select").removeClass("d-none");
  $("#n-btn-create").removeClass("d-none");

  $("#n-btn-delete").addClass("d-none");
  $("#n-btn-unselect").addClass("d-none");
}
