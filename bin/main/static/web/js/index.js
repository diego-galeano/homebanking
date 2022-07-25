Vue.createApp({
    data (){
        return {
            email:"",
            password:"",
            firstName:"",
            lastName:"",
            isRegistered: true            
        };
    },
  

        methods:{
            createAccount() {
                axios.post('/api/clients/current/accounts', { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                    .then(response => {
    
                        window.location.href = "/accounts.html";
    
                    })
                },
            sendLogin(){  
                axios.post('/api/login',
                "email="+this.email+"&"+"password="+this.password,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => window.location.href = "accounts.html")    
            },
            sendRegister(){
                axios.post('/api/clients',
                "firstName="+this.firstName+"&lastName="+this.lastName+"&email="+this.email+"&password="+this.password,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => axios.post('/api/login',
                "email="+this.email+"&"+"password="+this.password,
                {headers:{'content-type':'application/x-www-form-urlencoded'}}))
                .then(response => window.location.href = "accounts.html")
            }  
              
            }
    }).mount("#app"); 
