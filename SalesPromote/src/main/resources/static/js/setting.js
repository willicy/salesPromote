window.onload = function () {};
let itemName;
function logout() {
  const url = "/user/logout";
  $.ajax({
    url: url,
    type: "GET",
    dataType: "json",
    success: function (json) {
      window.location.replace("/user/login");
    },
  });
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
function addBtn() {
  if ($("#n-itemmodal-inputName").val() == "") {
    swal("请填写内容", "", "error", { button: "确认" });
    return;
  }

  if (itemName == "size") {
    if ($("#n-itemmodal-inputNum").val() == "") {
      swal("请填写数量", "", "error", { button: "确认" });
      return;
    }
    const url = "/user/setting/size";
    $.ajax({
      url: url,
      data: {
        value: $("#n-itemmodal-inputName").val(),
        num: $("#n-itemmodal-inputNum").val(),
      },
      type: "put",
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          modifyItemModalBtn("size");
        } else {
          swal({
            title: "添加失败!",
            text: json.message,
            type: "error",
            button: "确认",
          });
        }
      },
    });
  } else {
    const url = "/user/setting/type";
    $.ajax({
      url: url,
      data: { value: $("#n-itemmodal-inputName").val() },
      type: "put",
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          modifyItemModalBtn("type");
        } else {
          swal({
            title: "添加失败!",
            text: json.message,
            type: "error",
            button: "确认",
          });
        }
      },
    });
  }
}
function delBtn(name, id) {
  if (id == "") {
    swal("删除错误，请重试", "", "error", { button: "确认" });
    return;
  }

  if (name == "size") {
    const url = "/user/setting/size";
    $.ajax({
      url: url,
      data: { id: id },
      type: "delete",
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          modifyItemModalBtn("size");
        } else {
          swal({
            title: "删除失败!",
            text: json.message,
            type: "error",
            button: "确认",
          });
        }
      },
    });
  } else {
    const url = "/user/setting/type";
    $.ajax({
      url: url,
      data: { id: id },
      type: "delete",
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          modifyItemModalBtn("type");
        } else {
          swal({
            title: "删除失败!",
            text: json.message,
            type: "error",
            button: "确认",
          });
        }
      },
    });
  }
}
//修改款内容modal
function modifyItemModalBtn(name) {
  $("#n-itemmodal-inputName").val("");
  $("#n-itemmodal-inputNum").val("");
  $("#n-tbody-item").html("");

  if (name == "size") {
    var url = "/user/setting/size";
    $.ajax({
      url: url,
      type: "GET",
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          const list = json.data;
          $("#ItemModalLabel").html("编辑 码数");
          list.forEach((element) => {
            var html =
              "<tr>" +
              "<td scope='row'><a>#{data}</a></td>" +
              "<td scope='row' class='col-5'><a>#{num}</a></td>" +
              "<td style='width: 10%'>" +
              "<button type='button ' onclick='delBtn(&apos;" +
              name +
              "&apos;,#{id})' class='btn btn-sm btn-danger' >X</button>" +
              "</td>" +
              "</tr>";

            html = html.replace(/#{id}/g, element.id);
            html = html.replace("#{data}", element.data);
            html = html.replace("#{num}", element.num);
            $("#n-tbody-item").append(html);
          });
          $("#n-itemmodal-inputNum").removeClass("d-none");
          $("#ItemModal").modal("show");
          itemName = name;
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
  } else {
    var url = "/user/setting/type";
    $.ajax({
      url: url,
      type: "GET",
      dataType: "json",
      success: function (json) {
        if (json.state == 200) {
          const list = json.data;
          $("#ItemModalLabel").html("编辑 品名");
          list.forEach((element) => {
            var html =
              "<tr>" +
              "<td scope='row'><a>#{data}</a></td>" +
              "<td style='width: 10%'>" +
              "<button type='button ' onclick='delBtn(&apos;" +
              name +
              "&apos;,#{id})' class='btn btn-sm btn-danger' >X</button>" +
              "</td>" +
              "</tr>";

            html = html.replace(/#{id}/g, element.id);
            html = html.replace("#{data}", element.data);
            $("#n-tbody-item").append(html);
          });
          $("#n-itemmodal-inputNum").addClass("d-none");
          $("#ItemModal").modal("show");
          itemName = name;
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
}
