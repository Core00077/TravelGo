//内容引入
(function () {
    document.writeln("<link rel=\'stylesheet\' href=\'../css/templet/lightDialog.css\'>");
    document.writeln("<div id=\'light-dialog\'>");
    document.writeln("    <div class=\'dialog-body\'>");
    document.writeln("        <i class=\'fa fa-commenting-o\'></i>");
    document.writeln("        <p class=\'dialog-text\'>ss</p>");
    document.writeln("    </div>");
    document.writeln("</div> ");
})();

(function (window) {
    var lightDialog = document.querySelector("#light-dialog");
    var innerMessage = document.querySelector("#light-dialog p");
    //对话框
    var dialog = {
        showDialog: function (message) {
            innerMessage.innerText = message;
            lightDialog.style.zIndex = 10000;
            lightDialog.style.visibility = "visible";
            lightDialog.style.opacity = 0.85;
        },
        closeDialog: function () {
            lightDialog.style.opacity = "0";
            lightDialog.style.visibility = "hidden";
            lightDialog.style.zIndex = 0;
        }
    }
    window.dialog = dialog;
})(window);