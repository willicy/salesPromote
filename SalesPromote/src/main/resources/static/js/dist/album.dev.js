"use strict";

window.onload = function () {//init();
};

function tickPhoto(photoframe) {
  if ($(photoframe).attr("class") == "n-photoframe") {
    $(photoframe).attr("class", "n-photoframeticked");
    $(photoframe).children(".n-phototick").attr("class", "n-phototicked");
    $(photoframe).find(".n-phototick-img").attr("class", "n-phototicked-img");
  } else {
    $(photoframe).attr("class", "n-photoframe");
    $(photoframe).children(".n-phototicked").attr("class", "n-phototick");
    $(photoframe).find(".n-phototicked-img").attr("class", "n-phototick-img");
  }
}