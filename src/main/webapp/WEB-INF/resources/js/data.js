var DataTable = function(element,options) {
  this.$element = $(element);
  this.title = this.$element.attr('data-title');
  this.listUrl= this.$element.attr('data-list-url');
  this.showUrl= this.$element.attr('data-show-url');
  this.search= this.$element.attr('data-search');
  this.$buttons= this.$element.find('button');
  this.columns = options.columns;
  this.renderDatagridHTML();
  this.renderButtons();
  this.bindButtonEvent();
  this.bindSelectEvent();
  this.bindHoverEvent();
  this.ajaxData();

};
DataTable.prototype = {
  constructor: DataTable,
  renderDatagridHTML:function(){
    var html= '<form method="post"><table id="MyGrid" class="table table-bordered datagrid">' +
      '<thead><tr><th><div class="datagrid-header-title">'+this.title+'</div></th></tr><tr><th><div id ="datagrid-button-area" class="datagrid-header-title"></div>'+
      '<div class="datagrid-header-left"></div>' +
      '<div class="datagrid-header-right"><div class="input-append search">' +
      '<input type="text" class="input-medium" placeholder="Search"><button class="btn"><i class="icon-search"></i></button>' +
      '</div></div>' +
      '</th></tr></thead>' +
      '<tfoot><tr><th>' +
      '<div class="datagrid-footer-left" style="display:none"><div class="grid-controls">' +
      '<span><span class="grid-start"></span> - <span class="grid-end"></span> of <span class="grid-count"></span></span>' +
      '<select class="grid-pagesize"><option>10</option><option>20</option><option>50</option></select>' +
      '<span>Per Page</span>' +
      '</div></div>' +
      '<div class="datagrid-footer-right" style="display:none"><div class="grid-pager">' +
      '<button class="btn grid-prevpage"><i class="icon-chevron-left"></i></button>' +
      '<span>Page</span>' +
      '<div class="input-append dropdown combobox">' +
      '<input class="span1" type="text"><button class="btn" data-toggle="dropdown"><i class="caret"></i></button>' +
      '<ul class="dropdown-menu"></ul>' +
      '</div>' +
      '<span>of <span class="grid-pages"></span></span>' +
      '<button class="btn grid-nextpage"><i class="icon-chevron-right"></i></button>' +
      '</div></div>' +
      '</th></tr></tfoot></table></form>';
    this.$element.append(html);
    if(this.search == "false"){
      $(".datagrid-header-right").remove();
    }
  },
  renderButtons:function(){
    var self = this;
    var button_area = $("#datagrid-button-area");
    $.each(this.$buttons,function(index,button){
      var button = $(button);
      var button_type = button.attr("data-button-type");
      var icon = '<i></i>';
      if(button_type == "add"){
        button.attr("_method","post");
        $("<i/>").addClass("icon-plus").prependTo(button);

      }else if(button_type == "edit"){
        button.attr("_method","put");
        $("<i/>").addClass("icon-edit").prependTo(button);
      }else if(button_type == "delete"){
        button.attr("_method","delete");
        $("<i/>").addClass("icon-trash").prependTo(button);
      }else if(button_type == "status"){
        button.attr("_method","put");
        $("<i/>").addClass("icon-retweet").prependTo(button);
      }
      button.addClass("btn");
      button.addClass("datagrid-button");
      button_area.append(button);

    });
  },
  bindButtonEvent:function(){
    var self = this;
    $(".datagrid-button").live("click",function(){
      var _method = $(this).attr("_method");
      var url = $(this).attr("data-button-url");
      var data_selected = $("datagrid-table .datagrid-selected");
      if(data_selected.length == 0){
        return false;
      }
      //$("#_method").val(_method);
      $("form").attr("action",url+"/"+data_selected.attr("data-id"));
      $("form").submit();
    });
  },
  bindSelectEvent:function(){
    var self = this;
    $(".datagrid-table tbody tr").live("click",function(){
     var obj = $(".datagrid-selected");
     obj.removeClass("datagrid-selected");
     if(obj.attr("data-id") != $(this).attr("data-id")){
      $(this).addClass("datagrid-selected");
     }
    });
  },
  bindHoverEvent:function(){
    var self = this;
    $(".datagrid-table tbody tr").live("hover",function(){
        $(this).toggleClass("datagrid-hover");
      }
    );
  },
  ajaxData:function(){
    var self = this;
    $.get(this.listUrl,function(result){

      var dataSource = new StaticDataSource({
        columns:self.columns,
        data: result,
        delay: 200
      });
      $('#MyGrid').datagrid({ dataSource: dataSource,showUrl:self.showUrl })
    });
  },

};
$.fn.datatable= function (option) {
  return this.each(function () {
    var $this = $(this);
    var data = $this.data('datatable');
    var options = typeof option === 'object' && option;

    if (!data) $this.data('datatable', (data = new DataTable(this, options)));
    if (typeof option === 'string') data[option]();
  });
};
$.fn.datatable.Constructor = DataTable;





