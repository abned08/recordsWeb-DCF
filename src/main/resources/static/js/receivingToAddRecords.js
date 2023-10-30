/**
 *
 */

$(document).ready(function () {

    $('[data-toggle="tooltip"]').tooltip();

    // $('.myForm #recordout').first().focusin( function(){
    // 	$(this).data('val', $('.myForm #count').val());
    // });


    $('.myForm #recordName').change(function () {
        if ($('.myForm #recordout').val()!=null)
            $('.myForm #recordout').val('')
        var selectedRecordName = $(this).children("option:selected").attr("value");
        if (window.location.href.indexOf("OS_")!==-1){
            $.ajax({
                url: '/OS_countRecord/id=' + selectedRecordName,
                success: function (data) {
                    $('.myForm #count').val(data);
                    $('.myForm #countStatic').val(data);

                }

            });
            $.ajax({
                url: '/OS_getSafetyStock/id=' + selectedRecordName,
                success: function (data) {
                    $('.myForm #safetyStock').val(data);
                }

            });
        }else {
            $.ajax({
                url: '/countRecord/id=' + selectedRecordName,
                success: function (data) {
                    $('.myForm #count').val(data);
                    $('.myForm #countStatic').val(data);

                }

            });
            $.ajax({
                url: '/getSafetyStock/id=' + selectedRecordName,
                success: function (data) {
                    $('.myForm #safetyStock').val(data);
                }

            });
        }

    });


    $('.myForm #recordout').keyup(function () {
        const prev = parseInt($('.myForm #countStatic').val())
        const q = parseInt($('.myForm #recordout').val())
        const result = prev - q;
        $('.myForm #count').val(result);
        if (!$('.myForm #recordout').val())
            $('.myForm #count').val(prev)
        else {
            if (result < $('.myForm #safetyStock').val() && result > 0) {

                $('.myForm #submit').attr("disabled", false)
                $.alert({
                    title: 'تنبيـــه:',
                    content: 'انخفـــاظ مخــزون الأمـــان',
					rtl: true,
					theme:'light',
                    type: 'orange',
                    typeAnimated: true,
                    backgroundDismiss: false,
                    backgroundDismissAnimation: 'glow',
                    buttons: {

                        confirm: {
                            text: 'تأكيـد',
                            btnClass: 'btn-blue',
                            action: function () {

                            }
                        }
                    }
                });

                $('.myForm #count').val(result);
            } else if (result <= 0) {
                if (result===0)
                    $('.myForm #submit').removeAttr("disabled");
                else
                    $('.myForm #submit').attr("disabled", true)
                $('.myForm #count').removeClass('text-success')
                $('.myForm #count').addClass('text-danger')
                $.alert({
                    title: 'تنبيـــه:',
                    content: 'نفــاذ مخــزون الأمـــان',
                    rtl: true,
                    type: 'red',
                    typeAnimated: true,
                    backgroundDismiss: false,
                    backgroundDismissAnimation: 'glow',
                    buttons: {

                        confirm: {
                            text: 'تأكيـد',
                            btnClass: 'btn-blue',
                            action: function () {

                            }
                        }
                    }
                });
                $('.myForm #count').val(result);
            }else if (q == 0) {
                $('.myForm #submit').attr("disabled", true)
                $('.myForm #count').removeClass('text-success')
                $('.myForm #count').addClass('text-danger')

                $('.myForm #count').val(result);
            }
            else {
                $('.myForm #submit').removeAttr("disabled");
                $('.myForm #count').removeClass('text-danger')
                $('.myForm #count').addClass('text-success')
            }
        }


    });


    $('.myForm #recordout,.myForm #quantityDemand').on('input', function (event) {
        this.value = this.value.replace(/[^0-9]/g, '');
    });


    $('#exampleModal').draggable();
$('select').selectpicker();


    $('.newAddReceivingBtn, .table .editBtn').on('click', function (event) {
        event.preventDefault();


        var href = $(this).attr('href');
        var text = $(this).text();
        if (text == 'تعديل') {
            $.get(href, function (records, status) {
                $('.myForm #Id').val(records.id);
                $('.bootstrap-select .filter-option').text(records.recordName.recname);
                $('.myForm #recordName').val(records.recordName.id);
                // $('.myForm #recorddate').val(records.recorddate);
                $('.myForm #recordout').val(records.recordout);
                $('.myForm #quantityDemand').val(records.quantityDemand);
                $('.myForm #note').val(records.note);
                $('.myForm #count').val(records.count);
                $('.myForm #countStatic').val(records.count);
                $('.myForm #safetyStock').val(records.recordName.safetyStock);

               $('.myForm #invent').val(records.invent);


                // $('.myForm #count').val(records.recordName.id);

            });


            $('.myForm #exampleModal').modal();
        } else {
            $('.myForm #Id').val('');
            $('.myForm #recordName').val('');
            // $('.myForm #recorddate').val('');
            $('.myForm #recordout').val('');
            $('.myForm #quantityDemand').val('');
            $('.myForm #note').val('');
            $('.myForm #count').val('');
            $('.myForm #invent').val('');
            $('.myForm #safetyStock').val('');
            $('.myForm #exampleModal').modal();

        }

    });

    $('.table .delBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });

    $('#dataTable').DataTable(
        {
            fixedHeader: {
                header: true,
                footer: false,
                headerOffset: 0
            },
            order: [[0, 'asc']], lengthMenu: [[10, 25, 50, -1], [10, 25, 50, 'الكل']],

            "language": {

                "sProcessing": "جاري التحميل...",
                "sLengthMenu": "أظهر  _MENU_ من القائمة",
                "sZeroRecords": "لم يُعثر على أية سجلات",
                "sInfo": "إظهار _START_ إلى _END_ من أصل _TOTAL_ مُدخل",
                "sInfoEmpty": "يعرض 0 إلى 0 من أصل 0 سجلّ",
                "sInfoFiltered": "(منتقاة من مجموع _MAX_ مُدخل)",
                "sInfoPostFix": "",
                "sSearch": "ابحث:",
                "sUrl": "",
                "oPaginate": {
                    "sFirst": "الأول",
                    "sPrevious": "السابق",
                    "sNext": "التالي",
                    "sLast": "الأخير"
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


    $('[data-toggle="tooltip"]').tooltip();


    $(document).on("focusin", "#recorddate", function () {
        $(this).prop('readonly', true);
    });

    $(document).on("focusout", "#recorddate", function () {
        $(this).prop('readonly', false);
    });
});

(function () {
    'use strict';
    window.addEventListener('load', function () {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function (form) {
            form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();
