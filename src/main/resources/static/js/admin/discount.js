const app = angular.module("discount-app", []);
app.controller("discount-ctrl", function ($scope, $http) {

  $scope.discounts = [];
  $scope.cates = [];

  $scope.initialize = function () {
    $http.get(`/rest/discount`).then((resp) => {
      $scope.discounts = resp.data;
         $scope.discounts.forEach((count) => {
              count.activeDate = new Date(count.activeDate);
                count.expiredDate = new Date(count.expiredDate);
         });
    });
    $http.get(`/rest/category`).then((resp) => {
      $scope.cates = resp.data;
    });

    $scope.form = {
      active:true,
      activeDate: new Date()
     };
  };

  $scope.initialize();

    $scope.reset = function () {
       $scope.form = {
        active:true,
       };
     };
     $scope.edit = function (count) {
       $scope.form = angular.copy(count);
     };
   $scope.create = function () {
       let discount = angular.copy($scope.form);
       console.log(discount);
       $http
         .post(`/rest/discount`, discount)
         .then((resp) => {
           resp.data.activeDate = new Date(resp.data.activeDate);
           resp.data.expiredDate = new Date(resp.data.expiredDate);
           $scope.discounts.push(resp.data);
           $scope.reset();
           $("#discountModal").modal("hide");
           toastr.success("Create Success");
           setTimeout(()=>{
            location.reload();
           },1000);
         })
         .catch((err) => { 
          toastr.error("Create Fail");
         });
     };
      $scope.update = function () {
        let discount = angular.copy($scope.form);
        $http.put(`/rest/discount/${discount.id}`, discount).then(resp => {
          let index = $scope.discounts.findIndex(p => p.id == discount.id);
          $scope.discounts[index] = discount;
          $scope.reset();
          $("#discountModal").modal("hide");
          toastr.success("Update Success");
          setTimeout(()=>{
           location.reload();
          },1000);
        }).catch(err => {
          toastr.error("Create Fail");
        })
      };
       $scope.delete = function (count) {
          $http.delete(`/rest/discount/${count.id}`).then(resp => {
            let index = $scope.discounts.findIndex(p => p.id == count.id);
            $scope.discounts.splice(index, 1);
            $scope.reset();
            $("#discountModal").modal("hide");
            toastr.success("Delete Success");
            setTimeout(()=>{
           location.reload();
          },1000);
          }).catch(err => {
            toastr.error("Create Fail");
          })
        };

        // Pagination
        $scope.currentPageDiscount = 1;
        $scope.pageSizeDiscount = 5; // Số discount hiển thị mỗi trang

        $scope.totalPagesDiscount = function () {
          return Math.ceil($scope.discounts.length / $scope.pageSizeDiscount);
        };

        $scope.setPageDiscount = function (page) {
          if (page >= 1 && page <= $scope.totalPagesDiscount()) {
            $scope.currentPageDiscount = page;
          }
        };

        $scope.paginatedListDiscount = function () {
          const begin = ($scope.currentPageDiscount - 1) * $scope.pageSizeDiscount;
          const end = begin + $scope.pageSizeDiscount;

          return $scope.discounts.slice(begin, end);
        };

});
