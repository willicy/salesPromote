"use strict";

window.onload = function () {
  init();
  getAllFolder();
}; //取得所有文件夹


function getAllFolder() {
  var url = "/user/libary/folder";
  $.ajax({
    url: url,
    type: "GET",
    dataType: "json",
    success: function success(json) {
      $("#n-tbody").empty();
      var mainFolder = "<tr>" + "<th scope='row'/>" + "<td style='font-size: large; color: lightcoral;'><a href='./folder/main'>图库</a></td>" + "</tr>";
      $("#n-tbody").append(mainFolder);
      var list = json.data;
      list.forEach(function (element) {
        var html = "<tr>" + "<th scope='row'>" + "<input type='checkbox' style='cursor: pointer' value='#{id}' onchange='checkboxValueChange(this)'/>" + "</th>" + "<td><a href='./folder/#{id}'>#{name}</a></td>" + "</tr>";
        html = html.replace(/#{id}/g, element.id);
        html = html.replace("#{name}", element.name);
        $("#n-tbody").append(html);
      });
    }
  });
} //全选及反选


$("#checkAll").click(function () {
  if ($(this).prop("checked") == true) {
    $("input[type=checkbox]").each(function () {
      $(this).prop("checked", true);
    });
  } else {
    $("input[type=checkbox]").each(function () {
      $(this).prop("checked", false);
    });
  }
}); //创建文件夹

function createFolder() {
  var foldername = {
    foldername: document.getElementById("n-createModalInput-folderName").value
  };

  if (foldername != "") {
    $.ajax({
      url: "/user/libary/folder",
      type: "PUT",
      data: foldername,
      dataType: "json",
      success: function success(json) {
        if (json.state == 200) {
          window.location.replace("/user/libary");
        } else {
          swal("创建失败!", json.message, "error", {
            button: "确认"
          });
        }
      }
    });
  }
} //新增按钮


function createBtn() {
  document.getElementById("n-createModalInput-folderName").value = "";
  init();
} //重命名文件夹


function renameFolder() {
  var checkedFolderId;
  $("input[type=checkbox]").each(function () {
    if ($(this).prop("checked") == true) {
      checkedFolderId = $(this).val();
      return false;
    }
  });
  var folderInfo = {
    foldername: document.getElementById("n-renameModalInput-folderName").value,
    folderId: checkedFolderId
  };

  if (folderInfo.foldername != "" & folderInfo.folderId != "") {
    $.ajax({
      url: "/user/libary/folder",
      type: "PATCH",
      data: folderInfo,
      dataType: "json",
      success: function success(json) {
        if (json.state == 200) {
          window.location.replace("/user/libary");
        } else {
          swal("重命名失败!", json.message, "error", {
            button: "确认"
          });
        }
      }
    });
  }
} //新增按钮


function renameBtn() {
  document.getElementById("n-renameModalInput-folderName").value = "";
} //删除按钮


function delBtn() {
  var folderIds = new Array();
  $("input[type=checkbox]").each(function () {
    if ($(this).prop("checked") == true) {
      folderIds.push($(this).val());
    }
  });

  if (folderIds.length > 0) {
    $.ajax({
      url: "/user/libary/folder",
      type: "DELETE",
      data: {
        folderIds: folderIds
      },
      dataType: "json",
      success: function success(json) {
        if (json.state == 200) {
          window.location.replace("/user/libary");
        } else {
          swal("删除失败!", json.message, "error", {
            button: "确认"
          });
        }
      }
    });
  }
}
/**
 *根据选择个数able btns
 */


var countChecked = 0;

function checkboxValueChange(item) {
  //算chcked数量
  if (item.checked == true) countChecked += 1;else countChecked -= 1;
  changeButton();
}

function checkAllValueChange(item) {
  countChecked = 0; //算chcked数量

  if (item.checked == true) {
    $("input[type=checkbox]").each(function () {
      countChecked += 1;
    });
    countChecked -= 1;
  }

  console.log(countChecked);
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
  $("input[type=checkbox]").each(function () {
    $(this).prop("checked", false);
  });
  $("#checkAll").prop("checked", false);
}