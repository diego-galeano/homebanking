Vue.createApp({
    data() {
      return {
        client: [],
        accounts: [],
        loans:[],
        isActive: false,
        loanName:null,
        loanSelected:null,
        loanPayments: 0,
        payment: 0,
        amount: 0,
        maxAmount:0,
        accountNum: null,
        paymentAmount: 0,
        interestRate: 0,
        isInputChecked: false
      }
    },
    created(){
      axios.get('/api/clients/current')
      .then(res=> {
       this.client=res.data
       this.accounts=res.data.accounts.filter(account => account.active == true);
      })
      axios.get('/api/loans')
      .then(res => {
        this.loans = res.data
      })
},
    methods:{

      applyLoan(){ 
        const confirmInfo = `Prestamo ${this.loanName}.\n Monto: $${this.amount}\nCuenta destino nro: ${this.accountNum}\nCantidad de ${this.payment} cuotas de $${this.calcPayments}`     
        swal({
        title:"¿Deseas realizar esta operación?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
        text: confirmInfo,
    }).then((willLoan) => {
        if(willLoan){
        axios.post('/api/clients/current/loans',
        {id:this.loanSelected.id,
        amount: this.amount,
        payments: this.payment,
        accountNum: this.accountNum})
        .catch(res => swal(res.response.data,""))   
        swal("Préstamo Acreditado",{
          icon: "success"
        }) 
        .then(()=>location.reload()) ;
      }
    })
      },
     cancel(){
            window.location.href = "accounts.html"
        }, 
        
      selectLoanName($event){
          this.amount = null;
          this.payment= null;
          this.loanName = $event.target.value;
          this.loanSelected = this.loans.find(loan => loan.name == this.loanName);
          this.loanPayments = this.loanSelected.payments;
          this.maxAmount = this.loanSelected.maxAmount;
          this.interestRate = this.loanSelected.interestRate;
      },
      
      selectPayments($event){
          this.payment = $event.target.value;
      },
      selectAccountNum($event){
        this.accountNum = $event.target.value
      },
      isNumber(evt) {
        evt = (evt) ? evt : window.event;
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 46) {
          evt.preventDefault();;
        } else {
          return true;
        }
      },
      logout(){
        axios.post('/api/logout')
        .then(res => console.log('signed out!!!'))
        .then(res =>window.location.href = "index.html")
    },
    },
    computed:{
      calcPayments(){  
        if(this.payment > 0){
        let res = ((Number.parseFloat(this.amount) + (this.amount * (this.interestRate / 100))) / this.payment)
        return res.toFixed(2);
      }
        else 
        return 0;
      },
      totalCost(){
        if(this.payment > 0){
        let res = (this.amount * this.interestRate /100) + Number.parseFloat(this.amount);
        return res.toFixed(2);
        }
        else 0;
      },
      isFormFilled(){
        if(this.loanName && this.amount > 0 && this.payment && this.accountNum )
        return true;
        else return false;
      }
    }
  }).mount('#app')