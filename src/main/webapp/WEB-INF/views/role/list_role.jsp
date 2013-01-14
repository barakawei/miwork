<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>

<script  type="text/javascript">

$(function () {
  var columns= [{
    property: 'id',
    label: 'id',
    sortable: true
  }, {
    property: 'name',
    label: 'name',
    sortable: true,
    link: true
  }, {
    property: 'enable',
    label: 'enable',
    sortable: true
  }, {
    property: 'createDate',
    label: 'createDate',
    sortable: true
  }];

  $(".datagrid-table").datatable({columns:columns});


});
</script>

</head>
<body>
<div class="alert alert-error" style="display:none">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
  <strong>Error!</strong> Best check yo self, you're not looking too good.
</div>

<div class="datagrid-table" data-title="11111" data-list-url="listData" data-show-url="./" data-search="true">
  <button data-button-type="add" data-button-url="">add</button>
  <button data-button-type="edit" data-button-url="">edit</button>
  <button data-button-type="delete" data-button-url="delete">delete</button>
  <button data-button-type="status" data-button-url="">enable</button>
</div>
</body>
</html>

