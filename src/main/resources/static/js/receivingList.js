$(document).ready(function(){



    $('.myForm #receive_num,.myForm #memo_num,.myForm #mo_num').on('input', function (event) {
        this.value = this.value.replace(/[^0-9]/g, '');
    });

    $('#exampleModal').draggable();
    $('select').selectpicker();

    $('.newAddReceivingBtn, .table .editBtn').on('click',function(event){
        event.preventDefault();

        var href= $(this).attr('href');
        var text= $(this).text();
        if(text==='تعديل'){
            $.get(href,function(receiving,status){
                $('.myForm #id').val(receiving.id);
                $('.myForm #receive_num').val(receiving.receive_num);
                $('.myForm #receive_date').val(receiving.receive_date);
                $('.myForm #memo_num').val(receiving.memo_num);
                $('.myForm #memo_date').val(receiving.memo_date);
                $('.myForm #deliver_date').val(receiving.deliver_date);
                $('.myForm #deliver_to').val(receiving.deliver_to);
                $('.myForm #mo_num').val(receiving.mo_num);
                $('.myForm #mo_date').val(receiving.mo_date);
                $('.myForm #mo_owner').val(receiving.mo_owner);
                $('.bootstrap-select .filter-option').text(receiving.servicesReceiving.servicename);
                $('.myForm #servicename').val(receiving.servicesReceiving.serviceid);


            });


            $('.myForm #exampleModal').modal();
        } else{
            $('.myForm #id').val('');


            if(window.location.href.indexOf("OS_")!==-1){
                $.ajax({
                    url: '/OS_findLastAddOne',
                    success: function (data) {
                        $('.myForm #receive_num').val(data);
                    }

                });
            }else {
                $.ajax({
                    url: '/findLastAddOne',
                    success: function (data) {
                        $('.myForm #receive_num').val(data);
                    }

                });
            }

            $('.myForm #receive_date').val('');
            $('.myForm #memo_num').val('');
            $('.myForm #memo_date').val('');
            $('.myForm #deliver_date').val('');
            $('.myForm #deliver_to').val('');
            $('.myForm #mo_num').val('');
            $('.myForm #mo_date').val('');
            $('.myForm #mo_owner').val('');
            $('.myForm #servicename').val('');



            $('.myForm #exampleModal').modal();

        }

    });

    $('.table .delBtn').on('click',function(event){
        event.preventDefault();
        var href=$(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });

//	 $('#myTable').DataTable();
    $('#dataTable').DataTable(
        {
            fixedHeader : {
                header : true,
                footer : false,
                headerOffset: 0
            },
            order: [[2, 'desc']],lengthMenu: [[10, 25, 50, -1], [10, 25, 50, 'الكل']],

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

            }


        });

    $('#receive_date,#memo_date,#deliver_date,#mo_date').datepicker({
        format: "yyyy/mm/dd",
        todayBtn: "linked",
        clearBtn: true,
        language: "ar",
        multidate: false,
        calendarWeeks: true,
        autoclose: true,
        todayHighlight: true

    });



    $(document).on("focusin", "#receive_date", function() {
        $(this).prop('readonly', true);
    });
    $(document).on("focusout", "#receive_date", function() {
        $(this).prop('readonly', false);

    });$(document).on("focusin", "#memo_date", function() {
        $(this).prop('readonly', true);
    });
    $(document).on("focusout", "#memo_date", function() {
        $(this).prop('readonly', false);

    });$(document).on("focusin", "#deliver_date", function() {
        $(this).prop('readonly', true);
    });
    $(document).on("focusout", "#deliver_date", function() {
        $(this).prop('readonly', false);

    });$(document).on("focusin", "#mo_date", function() {
        $(this).prop('readonly', true);
    });
    $(document).on("focusout", "#mo_date", function() {
        $(this).prop('readonly', false);
    });
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
