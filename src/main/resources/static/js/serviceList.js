/**
 * 
 */


$(document).ready(function(){
	
	$('.newAddServiceBtn, .table .editBtn').on('click',function(event){
		event.preventDefault();
		
		var href= $(this).attr('href');
		var text= $(this).text();
		if(text=='تعديل'){
		$.get(href,function(services,status){
			$('.myForm #Id').val(services.serviceid);
			$('.myForm #servicename').val(services.servicename);
		});
		
		
		$('.myForm #exampleModal').modal();
	} else{
		$('.myForm #Id').val('');
		$('.myForm #code').val('');
		$('.myForm #recName').val('');
		$('.myForm #exampleModal').modal();
		
	}
	});
	
	$('.table .delBtn').on('click',function(event){
		event.preventDefault();
		var href=$(this).attr('href');
		$('#myModal #delRef').attr('href', href);
		$('#myModal').modal();
	});

	$('#dataTable').DataTable( {
		"language": {

				"sProcessing":   "جاري التحميل...",
				"sLengthMenu":   "أظهر  _MENU_ من القائمة",
				"sZeroRecords":  "لم يُعثر على أية سجلات",
				"sInfo":         "إظهار _START_ إلى _END_ من أصل _TOTAL_ مُدخل",
				"sInfoEmpty":    "يعرض 0 إلى 0 من أصل 0 سجلّ",
				"sInfoFiltered": "(منتقاة من مجموع _MAX_ مُدخل)",
				"sInfoPostFix":  "",
				"sSearch":       "ابحث:",
				"sUrl":          "",
				"oPaginate": {
				"sFirst":    "الأول",
					"sPrevious": "السابق",
					"sNext":     "التالي",
					"sLast":     "الأخير"
			}

		},
		autoFill: true,

	} );

});

(function() {
	'use strict';
	window.addEventListener('load', function() {
		// Fetch all the forms we want to apply custom Bootstrap validation styles to
		var forms = document.getElementsByClassName('needs-validation');
		// Loop over them and prevent submission
		var validation = Array.prototype.filter.call(forms, function(form) {
			form.addEventListener('submit', function(event) {
				if (form.checkValidity() === false) {
					event.preventDefault();
					event.stopPropagation();
				}
				form.classList.add('was-validated');
			}, false);
		});
	}, false);
})();