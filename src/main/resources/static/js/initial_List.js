/**
 * 
 */


$(document).ready(function(){


	$('[data-toggle="tooltip"]').tooltip();

	$('.myForm #recordin, .myForm #safetyStock').on('input', function (event) {
		this.value = this.value.replace(/[^0-9]/g, '');
	});

	$('.myForm #recordin').val('');


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