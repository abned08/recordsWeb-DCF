/**
 *
 */

$(document).ready(function(){

    var ordBondId = $('#orderBondId').val();



    $('[data-toggle="tooltip"]').tooltip();

    $('.myForm #recordName').change(function () {

        if ($('#recordName').selectedIndex!==-1)
            var selectedRecordName=$(this).children("option:selected").attr("value")
        else
            var selectedRecordName=0

        if ($('.myForm #recordin').val()!=null)
            $('.myForm #recordin').val('')

        if (window.location.href.indexOf("OS_")!==-1){
            $.ajax({
                url: '/OS_countRecord/id='+selectedRecordName,
                success: function (data) {
                    $('.myForm #count').val(data);
                }

            });

            $.ajax({
                url: '/OS_countRecordOrderBond/id='+selectedRecordName+'/idO='+ordBondId,
                success: function (data) {
                    $('.myForm #orderBondcount').val(data);
                }

            });
        }else {
            $.ajax({
                url: '/countRecord/id='+selectedRecordName,
                success: function (data) {
                    $('.myForm #count').val(data);
                }

            });

            $.ajax({
                url: '/countRecordOrderBond/id='+selectedRecordName+'/idO='+ordBondId,
                success: function (data) {
                    $('.myForm #orderBondcount').val(data);
                }

            });
        }




    });

    let ord=0;
    $('.myForm #recordin').first().focusin( function(){
        $(this).data('val', $('.myForm #count').val());
         ord=parseInt($('#orderBondcount').val())
    });

    $('.myForm #recordin').keyup(function () {
        const prev = parseInt($(this).data('val'))
        const q = parseInt($('.myForm #recordin').val())
        const result = prev + q;
        const resultOrderBond=ord-q;
        $('.myForm #count').val(result);
        $('.myForm #orderBondcount').val(resultOrderBond);


        if(!$('.myForm #recordin').val()){
            $('.myForm #count').val($(this).data('val'))
            $('.myForm #orderBondcount').val(ord)

        }


        if((resultOrderBond==0 && q==0) || (resultOrderBond<0 && q>0) || ord<0 || resultOrderBond<0 || q==0) {
            $('.myForm #submit').attr("disabled", true)
            $('.myForm #orderBondcount').removeClass('text-success')
            $('.myForm #orderBondcount').addClass('text-danger')
        }
        else {
            $('.myForm #submit').removeAttr("disabled");
            $('.myForm #orderBondcount').removeClass('text-danger')
            $('.myForm #orderBondcount').addClass('text-success')
        }

    });


    $('.myForm #recordin').on('input', function (event) {
        this.value = this.value.replace(/[^0-9]/g, '');
    });


    $('#exampleModal').draggable();
$('select').selectpicker();






    $('.newAddReceivingEnterBtn, .table .editBtn').on('click',function(event){
        event.preventDefault();

        var href= $(this).attr('href');
        var text= $(this).text();
        if(text=='تعديل'){
            $.get(href,function(records,status){
                $('.myForm #Id').val(records.id);
                $('.bootstrap-select .filter-option').text(records.recordName.recname);
                $('.myForm #recordName').val(records.recordName.id);
                // $('.myForm #recorddate').val(records.recorddate);
                $('.myForm #recordin').val(records.recordin);
                // $('.myForm #quantityDemand').val(records.quantityDemand);
                $('.myForm #note').val(records.note);
                $('.myForm #count').val(records.count);


                // $('.myForm #invent').val(records.invent);


                // $('.myForm #count').val(records.recordName.id);

            });


            $('.myForm #exampleModal').modal();
        } else{
            $('.myForm #Id').val('');
            $('.myForm #recordName').val('');
            // $('.myForm #recorddate').val('');
            $('.myForm #recordin').val('');
            // $('.myForm #quantityDemand').val('');
            $('.myForm #note').val('');
            $('.myForm #count').val('');
            // $('.myForm #invent').val('');
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
            order: [[0, 'asc']],lengthMenu: [[10, 25, 50, -1], [10, 25, 50, 'الكل']],

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





    // $(document).on("focusin", "#recorddate", function() {
    //     $(this).prop('readonly', true);
    // });
    //
    // $(document).on("focusout", "#recorddate", function() {
    //     $(this).prop('readonly', false);
    // });
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
