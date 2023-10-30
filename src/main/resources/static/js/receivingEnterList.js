$(document).ready(function(){





    $('#file').on('click',function() {
            $('.myForm #closeBtn').attr("disabled", true)
            $.ajax({
                url: '/removeFile/'+$('.myForm #id').val()+'/'+$('.myForm #deliver_enter_fileName').val(),
                success: function (data) {

                }

            });
        $('.myForm #fName').val('');
        $('.myForm #fPath').val('');
        $('.myForm #fType').val('');
        $('.myForm #fSize').val('');
        });


    $('.myForm #receive_enter_num').on('input', function (event) {
        this.value = this.value.replace(/[^0-9]/g, '');
    });



    $('#exampleModal').draggable();
$('select').selectpicker();


    $('.newAddReceivingEnterBtn, .table .editBtn').on('click',function(event){
        event.preventDefault();

        var href= $(this).attr('href');
        var text= $(this).text();
        if(text==='تعديل'){
            $.get(href,function(receivingEnter,status){
                $('.myForm #id').val(receivingEnter.id);
                $('.myForm #receive_enter_num').val(receivingEnter.receive_enter_num);
                $('.myForm #receive_enter_date').val(receivingEnter.receive_enter_date);
                $('.bootstrap-select .filter-option').text(receivingEnter.servicesReceivingEnter.servicename);
                $('.myForm #servicename').val(receivingEnter.servicesReceivingEnter.serviceid);
                $('.myForm #deliver_enter_to').val(receivingEnter.deliver_enter_to);
                $('.myForm #fName').val(receivingEnter.fileName);
                $('.myForm #fPath').val(receivingEnter.filePath);
                $('.myForm #fType').val(receivingEnter.fileType);
                $('.myForm #fSize').val(receivingEnter.fileSize);
                $('.myForm #divFile').mouseover(function () {
                    $('#cautionForFile').removeClass("d-none");
                }).mouseout(function () {
                    $('#cautionForFile').addClass("d-none");
                })


            });


            $('.myForm #exampleModal').modal();
        } else{
            $('.myForm #id').val('');
            if(window.location.href.indexOf("OS_")!==-1){

                $.ajax({
                    url: '/OS_findLastAddOneEnter',
                    success: function (data) {
                        $('.myForm #receive_enter_num').val(data);

                    }

                });
            }else {
                $.ajax({
                    url: '/findLastAddOneEnter',
                    success: function (data) {
                        $('.myForm #receive_enter_num').val(data);
                    }

                });
            }

            $('.myForm #receive_enter_date').val('');
            $('.myForm #servicename').val('');
            $('.myForm #deliver_enter_to').val('');
            $('.myForm #fName').val('');
            $('.myForm #fPath').val('');
            $('.myForm #fType').val('');
            $('.myForm #fSize').val('');

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

    $('#receive_enter_date').datepicker({
        format: "yyyy/mm/dd",
        todayBtn: "linked",
        clearBtn: true,
        language: "ar",
        multidate: false,
        calendarWeeks: true,
        autoclose: true,
        todayHighlight: true

    });



    $(document).on("focusin", "#receive_enter_date", function() {
        $(this).prop('readonly', true);
    });
    $(document).on("focusout", "#receive_enter_date", function() {
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


