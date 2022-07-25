Vue.createApp({
    data() {
      return {
        client: [],
        accounts: [],
        isActive: false,
        accountType:null,
        amount: null,
        description: null,
        originAccountNum: null,
        destinationAccountNum: null,
        accountSeleted: null,
        isInputchecked: false
      }
    },
    created(){
      axios.get('/api/clients/current')
      .then(res=> {
       this.client=res.data
       this.accounts=res.data.accounts.filter(account => account.active == true)
      })
},
    methods:{
      makeTransfer(){ 
        const confirmInfo = `Monto: $${this.amount}\nCuenta nro: ${this.originAccountNum}\nCuenta destino nro: ${this.destinationAccountNum}\n Descripción: ${this.description}`     
        swal({
        title:"¿Deseas realizar esta transacción?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
        text: confirmInfo,
        }).then((willTransfer) => {
        if(willTransfer){
        axios.post('/api/transactions',
        `amount=${this.amount}&description=${this.description}&originAccountNum=${this.originAccountNum}&destinationAccountNum=${this.destinationAccountNum}`,
        {headers:{'content-type':'application/x-www-form-urlencoded'}})
        .catch(res => swal(res.response.data,""))
        swal("Transacción completada",{
          icon: "success"
        })
        .then(()=>location.reload());

      }
    })
      },
      selectAccountType($event){
        this.accountType = $event.target.value
      },
      selectAccountNum($event){
        this.originAccountNum = $event.target.value
      },
      selectOwnAccount($event){
        this.originAccountNum = $event.target.value;
        this.accountSeleted = this.accounts.filter(account => account.number == this.originAccountNum) 
      },
      selectAccountDetination($event){
        this.destinationAccountNum = $event.target.value.split(" ",1)[0];
      },
      isNumber(evt) {
        evt = (evt) ? evt : window.event;
        let charCode = (evt.which) ? evt.which : evt.keyCode;
        if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 46) {
          evt.preventDefault();;
        } else {
          return true;
        }
      },
     
      cancel(){
             window.location.href = "accounts.html"
         }, 

      logout(){
        axios.post('/api/logout')
        .then(res => console.log('signed out!!!'))
        .then(res => window.location.href = "index.html")
    },
    },
    computed:{
      isFormFilled(){
        if(this.accountType && this.originAccountNum && this.destinationAccountNum && this.description)
          return true;  
        else
          return false;
      },
      getAvailableAmount(){
        let result = this.accountSeleted.map(account => account.balance - this.amount).toString();
        return result;
      },
      getTransactions(){
        let transactions;
        this.accountSeleted ? this.accountSeleted.forEach(element => {
          transactions = element.transactions
        }) : 'Sin información';
        return transactions;
      },
      balance(){
        let balance;
        this.accountSeleted ? this.accountSeleted.forEach(e => balance =  e.balance): null;
        return balance;
      }
    }
  }).mount('#app')