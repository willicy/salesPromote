window.onload = function () {
  init();
  getAllCustomer();
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
    data: { customer: $("#n-customer").val() },
    type: "GET",
    dataType: "json",
    success: function (json) {
      $("#n-tbody").empty();

      var list = json.data;
      list.forEach((element) => {
        var html =
          "<tr>" +
          "<td scope='row' onclick='window.location.replace(&apos;./send/page?id=#{id}&name=#{name}&apos;)' ><a>#{name}</a></td>" +
          "<td onclick='window.location.replace(&apos;./send/page?id=#{id}&name=#{name}&apos;)' ><a>#{nickname}</a></td>" +
          "<td onclick='window.location.replace(&apos;./send/page?id=#{id}&name=#{name}&apos;)'  ><a>#{country}</a></td>" +
          "<td onclick='window.location.replace(&apos;./send/page?id=#{id}&name=#{name}&apos;)'  ><a>#{district}</a></td>" +
          "</tr>";

        html = html.replace(/#{id}/g, element.id);
        html = html.replace(/#{name}/g, element.name);
        html = html.replace("#{nickname}", element.nickname);
        html = html.replace("#{district}", element.district);
        html = html.replace("#{country}", element.country);
        $("#n-tbody").append(html);
      });
    },
  });
}

/**
 * 初始化
 */
function init() {
  $("input[type=checkbox]").each(function () {
    $(this).prop("checked", false);
  });
}
