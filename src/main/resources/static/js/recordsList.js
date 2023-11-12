/**
 *
 */
// $(window).on('load', function () {
//     let s = $('.estText h2').text();
//     let trimmed = s.trim();
//     if (!trimmed.length) {
//         $('.myFormEst #exampleModalEst').modal('show');
//     }
//
// });


$(document).ready(function () {

    $('[data-toggle="tooltip"]').tooltip();

    $('.myForm #quantity').first().focusin(function () {
        $(this).data('val', $('.myForm #count').val());
        window.v = parseInt($(this).data('val'))
    });

    function countRecord() {
        const prev = window.v
        // alert('prev='+window.v)
        const q = parseInt($('.myForm #quantity').val())
        // alert('q='+q)
        if ($('.myForm #recordout').prop("checked") === true) {
            const result = prev - q;
            // alert('result='+result)

            $('.myForm #count').val(result);
            if (!$('.myForm #quantity').val())
                $('.myForm #count').val(window.v)
            if (result < 0) {
                $('.myForm #submit').attr("disabled", true)
                $('.myForm #count').removeClass('text-success')
                $('.myForm #count').addClass('text-danger')
            } else {
                $('.myForm #submit').removeAttr("disabled");
                $('.myForm #count').removeClass('text-danger')
                $('.myForm #count').addClass('text-success')
            }
        } else {
            const result = prev + q;
            $('.myForm #count').val(result);
            if (!$('.myForm #quantity').val())
                $('.myForm #count').val(window.v)
            if (result < 0) {
                $('.myForm #submit').attr("disabled", true)
                $('.myForm #count').removeClass('text-success')
                $('.myForm #count').addClass('text-danger')
            } else {
                $('.myForm #submit').removeAttr("disabled");
                $('.myForm #count').removeClass('text-danger')
                $('.myForm #count').addClass('text-success')
            }
        }
    }

    $('.myForm #recordName').change(function () {
        var selectedRecordName = $(this).children("option:selected").attr("value");
        if(window.location.href.indexOf("OS_")!==-1){
            $.ajax({
                url: 'OS_countRecord/id=' + selectedRecordName,
                success: function (data) {
                    $('.myForm #count').val(data);
                }

            });
        }else {
            $.ajax({
                url: 'countRecord/id=' + selectedRecordName,
                success: function (data) {
                    $('.myForm #count').val(data);
                }

            });
        }
        $.ajax({
            url: 'countRecord/id=' + selectedRecordName,
            success: function (data) {
                $('.myForm #count').val(data);
            }

        });
    });

    $('.myForm #quantity').on('input', function (event) {
        this.value = this.value.replace(/[^0-9]/g, '');
    });


    $('.myForm #quantity').keyup(function () {
        countRecord();
    });

    $("#input_container input").on('change', function () {
        $(this).data('val')
        if ($('.myForm #recordin').prop("checked") === true) {
            setTimeout(function () {
                $('.myForm #recIn').prop('checked', true)
            }, 20)

        } else {
            setTimeout(function () {
                $('.myForm #recIn').prop('checked', null)
            }, 20)
        }
        countRecord();
    });

    $('#exampleModal').draggable();
$('select').selectpicker();


    $('.newAddRecordsBtn, .table .editBtn').on('click', function (event) {
        event.preventDefault();


        var href = $(this).attr('href');
        var text = $(this).text();
        if (text == 'تعديل') {
            $.get(href, function (records, status) {
                $('.myForm #Id').val(records.id);
                $('.myForm #recordName').val(records.recordName.id);
                $('.myForm #recorddate').val(records.recorddate);
                $('.myForm #count').val(records.count);

                $('.myForm #recordin').prop('checked', function (i, val) {
                    val = records.recordin;
                    return val == 0 ? null : true;
                });
                $('.myForm #recordout').prop('checked', function (i, val) {
                    val = records.recordout;
                    return val == 0 ? null : true;
                });


                if ($('.myForm #recordin').prop("checked") === true) {
                    setTimeout(function () {
                        $('.myForm #recIn').prop('checked', true)
                    }, 20)

                } else {
                    setTimeout(function () {
                        $('.myForm #recIn').prop('checked', null)
                    }, 20)
                }


                $('.myForm #invent').val(records.invent);
                if (records.recordin != 0)
                    $('.myForm #quantity').val(records.recordin);
                else
                    $('.myForm #quantity').val(records.recordout);
                $('.myForm #servicename').val(records.services.serviceid);

                // $('.myForm #count').val(records.recordName.id);

            });


            $('.myForm #exampleModal').modal();
        } else {
            $('.myForm #Id').val('');
            $('.myForm #recordName').val('');
            $('.myForm #recorddate').val('');
            $('.myForm #quantity').val('');
            $('.myForm #count').val('');
            $('.myForm #recordout').attr('checked', 'checked');
            $('.myForm #invent').val('');
            $('.myForm #servicename').val('');
            $('.myForm #exampleModal').modal();

        }

    });

    $('.table .delBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });

//	 $('#myTable').DataTable();
    $('#dataTable').DataTable(
        {
            fixedHeader: {
                header: true,
                footer: false,
                headerOffset: 0
            },

            columnDefs: [
                {
                    render: function (data, type, full, meta) {
                        return "<div class='text-wrap'>" + data + "</div>";
                    },
                    targets: 6,

                },
                { "width": "40%", "targets": 6 }
            ],
            order: [[0, 'desc']], lengthMenu: [[10, 25, 50, -1], [10, 25, 50, 'الكل']],
            initComplete: function () {
                this.api().columns([1, 3, 4, 5, 6]).every(function () {
                    var column = this;
                    var select = $('<select><option value=""></option></select>')
                        .appendTo($(column.footer()).empty())
                        .on('change', function () {
                            var val = $.fn.dataTable.util.escapeRegex(
                                $(this).val()
                            );

                            column
                                .search(val ? '^' + val + '$' : '', true, false)
                                .draw();
                        });

                    column.data().unique().sort().each(function (d, j) {
                        select.append('<option value="' + d + '">' + d + '</option>');
                    });
                }).columns([2]).every(function () {
                    var that = this;
                    var title = $(this).text();
                    var column = this;
                    var select = $('<input type="number" data-placement="top" data-toggle="tooltip" id="cYear" maxlength="4"  placeholder="فلترة بالسنوات"  title="فلترة بالسنوات"  pattern="\d{4}" dir="rtl" onkeypress="return event.charCode>=48 && event.charCode<=57" required/>')
                        .appendTo($(column.header())).on('change keyup', function () {
                            if (that.search() !== this.value) {
                                that
                                    .search(this.value)
                                    .draw();

                            }

                        });
                });


            },
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


    var currentYear = new Date().getFullYear();
    setTimeout(function () {
        $('#cYear').val(currentYear).change();
    }, 20)

    $('#cYear').on('click', function (e) {
        e.stopPropagation();
    });
    $('#cYear').on('mouseup', function (e) {
        e.stopPropagation();
    });


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

