/**
 *
 */



$(document).ready(function () {

    $('[data-toggle="tooltip"]').tooltip();


    $('.myForm #safetyStock').on('input', function (event) {
        this.value = this.value.replace(/[^0-9]/g, '');
    });

    $('#exampleModal').draggable();
$('select').selectpicker();


    $('.table .editBtn').on('click', function (event) {
        event.preventDefault();


        var href = $(this).attr('href');
        var text = $(this).text();
        if (text == 'تعديل') {
            $.get(href, function (records, status) {
                $('.myForm #Id').val(records.id);
                $('.myForm #safetyStock').val(records.safetyStock);

            });

            $('.myForm #exampleModal').modal();
        } else {
            $('.myForm #Id').val('');
            $('.myForm #safetyStock').val('');

            $('.myForm #exampleModal').modal();

        }

    });



//	 $('#myTable').DataTable();
    $('#dataTable').DataTable(
        {
            fixedHeader: {
                header: true,
                footer: false,
                headerOffset: 0
            },
            "createdRow": function( row, data, dataIndex){
                if( data[4] <= 0  ){
                    $(row).addClass('table-danger');
                }
            },


            order: [[2, 'asc']], lengthMenu: [[10, 25, 50, -1], [10, 25, 50, 'الكل']],

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

