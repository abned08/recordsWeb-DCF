$(document).ready(function(){





    $('.myForm #serviceAdd').on('input',function () {
        if ($('.myForm #serviceAdd').val()!=''){
            $('.myForm #servicename').prop('selectedIndex',0).removeAttr('required').attr('disabled',true)
            $('.myForm #serviceAdd').attr('required',true)
        }else {
            $('.myForm #servicename').attr('required',true).removeAttr('disabled')
            $('.myForm #serviceAdd').removeAttr('required')

        }


    });

    $('#file').on('click',function() {
        $('.myForm #closeBtn').attr("disabled", true)
        if(window.location.href.indexOf("OS_")!==-1){
            $.ajax({
                url: '/OS_removeFileOrederBond/'+$('.myForm #id').val()+'/'+$('.myForm #deliver_enter_fileName').val(),
                success: function (data) {

                }

            });
        }else {
            $.ajax({
                url: '/removeFileOrederBond/'+$('.myForm #id').val()+'/'+$('.myForm #deliver_enter_fileName').val(),
                success: function (data) {

                }

            });
        }

        $('.myForm #fName').val('');
    });






    $('#exampleModal').draggable();
    $('select').selectpicker();

    $('.newAddOrderBondBtn, .table .editBtn').on('click',function(event){
        event.preventDefault();

        var href= $(this).attr('href');
        var text= $(this).text();
        if(text==='تعديل'){
            $.get(href,function(orderBond,status){
                $('.myForm #id').val(orderBond.id);
                $('.myForm #orderNum').val(orderBond.orderNum);
                $('.myForm #orderDate').val(orderBond.orderDate);
                $('.bootstrap-select .filter-option').text(orderBond.servicesOrderBond.servicename);
                $('.myForm #servicename').val(orderBond.servicesOrderBond.serviceid);
                $('.myForm #fName').val(orderBond.fileName);
                $('.myForm #divFile').mouseover(function () {
                    $('#cautionForFile').removeClass("d-none");
                }).mouseout(function () {
                    $('#cautionForFile').addClass("d-none");
                })


            });


            $('.myForm #exampleModal').modal();
        } else{
            $('.myForm #id').val('');
            if (window.location.href.indexOf("OS_")!==-1){
                $.ajax({
                    url: '/OS_findLastAddOneOrder',
                    success: function (data) {
                        $('.myForm #orderNum').val(data);
                    }

                });
            }else {
                $.ajax({
                    url: '/findLastAddOneOrder',
                    success: function (data) {
                        $('.myForm #orderNum').val(data);
                    }

                });
            }

            $('.myForm #orderDate').val('');
            $('.myForm #servicename').val('');
            $('.myForm #fName').val('');

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
            "createdRow": function( row, data, dataIndex){
                if( data[4]===("تم تسليم كل المطبوعات")   ){
                    $(row).addClass('table-success');
                    this.$('.delBtn').remove();
                    this.$('.editBtn').remove();
                }
                else if( data[4]===("تسليم جزئي")   ){
                    this.$('.delBtn').remove();
                    this.$('.editBtn').show();
                }
                else if( data[4]===("لم يتم التسليم بعد")   ){
                    this.$('.delBtn').show();
                    this.$('.editBtn').show();
                }
            },
            order: [[2, 'desc']],
            lengthMenu: [[10, 25, 50, -1], [10, 25, 50, 'الكل']],

            "language": {

                "sProcessing":   "جاري التحميل...",
                "sLengthMenu":   "أظهر  _MENU_ من القائمة",
                "sZeroRecords":  "لــــم يُعثــر علـــى أيـــة سجــــلات",
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

    $('#orderDate').datepicker({
        format: "yyyy/mm/dd",
        todayBtn: "linked",
        clearBtn: true,
        language: "ar",
        multidate: false,
        calendarWeeks: true,
        autoclose: true,
        todayHighlight: true

    });



    $(document).on("focusin", "#orderDate", function() {
        $(this).prop('readonly', true);
    });
    $(document).on("focusout", "#orderDate", function() {
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


