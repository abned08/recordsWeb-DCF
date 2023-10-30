/**
 *
 */


$(document).ready(function(){

    $('[data-toggle="tooltip"]').tooltip();

    $('.myForm #quantityDemand').on('input', function (event) {
        this.value = this.value.replace(/[^0-9]/g, '');
    });

    // var t=document.getElementById('establishment').innerHTML
    // $('#establishmentCopy').text(t);


    $('.table .editBtn').on('click',function(event){
        event.preventDefault();

        var href= $(this).attr('href');
        var text= $(this).text();
        if(text=='تعديل'){
            $.get(href,function(report,status){
                $('.myForm #id').val(report.id);
                $('.myForm #recordName').val(report.recordName);
                $('.myForm #code').val(report.code);
                $('.myForm #quantity').val(report.quantity);
                $('.myForm #quantityDemand').val(report.quantityDemand?report.quantityDemand!==0:'');
                $('.myForm #note').val(report.note);
            });


            $('.myForm #exampleModal').modal();
        } else{
            $('.myForm #id').val('');
            $('.myForm #recordName').val('');
            $('.myForm #code').val('');
            $('.myForm #quantity').val('');
            $('.myForm #quantityDemand').val('');
            $('.myForm #note').val('');



            $('.myForm #exampleModal').modal();

        }
    });

    $('.table .delBtn').on('click',function(event){
        event.preventDefault();
        var href=$(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });

    $('#dataTable').DataTable(
        {
            draggable:true,
            fixedHeader : {
                header : true,
                footer : false,
                headerOffset: 0
            },
            order: [[1, 'asc']],lengthMenu: [[10, 25, 50, -1], [10, 25, 50, 'الكل']],

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

    } );


    $('#date-container input').datepicker({
        format: "yyyy/mm/dd",
        todayBtn: "linked",
        clearBtn: true,
        language: "ar",
        multidate: false,
        calendarWeeks: true,
        autoclose: true,
        todayHighlight: true
    });


    $(document).on("focusin", "#dateReport", function() {
        $(this).prop('readonly', true);
    });

    $(document).on("focusout", "#dateReport", function() {
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
