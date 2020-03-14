//https://www.geeksforgeeks.org/find-subarray-with-given-sum-in-array-of-integers/

// ██████╗ ██████╗ ██████╗ ███████╗██████╗ ██╗   ██╗███╗   ██╗██╗  ██╗███╗   ██╗ ██████╗ ██╗    ██╗███╗   ██╗
// ██╔════╝██╔═══██╗██╔══██╗██╔════╝██╔══██╗██║   ██║████╗  ██║██║ ██╔╝████╗  ██║██╔═══██╗██║    ██║████╗  ██║
// ██║     ██║   ██║██║  ██║█████╗  ██████╔╝██║   ██║██╔██╗ ██║█████╔╝ ██╔██╗ ██║██║   ██║██║ █╗ ██║██╔██╗ ██║
// ██║     ██║   ██║██║  ██║██╔══╝  ██╔══██╗██║   ██║██║╚██╗██║██╔═██╗ ██║╚██╗██║██║   ██║██║███╗██║██║╚██╗██║
// ╚██████╗╚██████╔╝██████╔╝███████╗██║  ██║╚██████╔╝██║ ╚████║██║  ██╗██║ ╚████║╚██████╔╝╚███╔███╔╝██║ ╚████║
//  ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝  ╚══╝╚══╝ ╚═╝  ╚═══╝
#include<bits/stdc++.h>
#include<stdio.h>
#include<algorithm>
#include<stack>
#include<queue>
#include<list>
#include<tuple>
#include<vector>
#include<bitset>
#include<unordered_map>
#define testcases ll t; cin>>t; while(t--)
using namespace std;
#define fio ios_base::sync_with_stdio(false)
#define For(i,a,b) for(ll i=a; i<=b; i++)
#define RFor(i,a,b) for(ll i=a, i>=b; i--)
#define pii pair<ll,ll>
#define N 300005
#define ll long long
#define pb push_back
#define mp(x,y) make_pair(x,y)
#define debug(x) cout<< #x <<"="<<x<<endl
#define INF 1000000009
#define MOD 1000000007
#define S(x) scanf("%d", &x)
#define S2(x,y) scanf("%d%d", &x,&y)
#define P(X) printf("%d\n", x)
#define PI 3.1415926535
using namespace std;
typedef priority_queue<ll, vector<ll > > max_pq;
typedef priority_queue<ll, vector<ll> , greater<ll > > min_pq;
ll toint(const string &s) {stringstream ss; ss << s; ll x; ss >> x; return x;}
string tostring ( ll number ){stringstream ss; ss<< number; return ss.str();}

#define TRACE
#ifdef TRACE
#define trace(...) __f(#__VA_ARGS__, __VA_ARGS__)
    template <typename Arg1>
    void __f(const char* name, Arg1&& arg1){
        cout << name << " : " << arg1 << std::endl;
        //use cerr if u want to display at the bottom
    }
    template <typename Arg1, typename... Args>
    void __f(const char* names, Arg1&& arg1, Args&&... args){
        const char* comma = strchr(names + 1, ','); cout.write(names, comma - names) << " : " << arg1<<" | ";__f(comma+1, args...);
    }
#else
#define trace(...)
#endif
#define nn 2000005

template<typename... T>
void read(T&&...args){ //
    ((cin>>args), ...);
}

template<typename... T>
void write(T...args){
    ((cout<<args<<" "), ...);
}

class reverseproxy{
    string domain_name;
    int machines;
    vector<string> ips;
    public:
     reverseproxy(string domain_name, int machines, vector<string> ips){
        this->domain_name = domain_name;
        this->machines = machines;
        this->ips = ips;
    }
    string getname(){
        return domain_name;
    }
    int getMachine(){
        return machines;
    }

    vector<string> getips(){
        return ips;
    }

};


tuple<int,int,string> parsequery(string str, int n1){
    size_t last = 0;
    size_t next = 0;
    int finalip;
    bool status;
    string status_ip;
    while ((next = str.find('/', last)) != string::npos) 
    {   string str2 = str.substr(last, next-last);
        for(int i=1; i<=n1; i++){
        if(str2.find("ww"+tostring(i))!=string::npos){
            finalip=i;
        }
    }last = next + 1; 
     } 
     if(str.substr(last).find("machine_up") != string::npos) {
         status=true;
         while ((next = str.find('=', last)) != string::npos) 
            {last = next + 1;} 
        string status_ip =  str.substr(last);}
     else if(str.substr(last).find("machine_down") != string::npos) {
         status=false;
         while ((next = str.find('=', last)) != string::npos) 
            {last = next + 1;} 
        string status_ip = str.substr(last);
        }
        return make_tuple(finalip, status, status_ip);
}


int main()
{
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    #ifndef ONLINE_JUDGE
    freopen("./../input.txt","r",stdin);
    freopen("./../output.txt","w",stdout);
    #endif

    // testcases{
        int n; //number of machines
        cin>>n;
        vector<string> ips;
        for(int i=0; i<=n-1; i++){
            string val;
            cin>>val;
            //trace(val,n);
            ips.pb(val);
        }
        int r;
        cin>>r;
        vector<reverseproxy> rps;
        for(int i=0; i<=r-1; i++){
            string domain_name;
            cin>>domain_name;
            int n;
            cin>>n;
            vector<string> ips;
            for(int j=0; j<=r-1; j++){
                string val;
                cin>>val;
                ips.push_back(val);
            }
            trace(domain_name);
            reverseproxy r (domain_name, n, ips);
            rps.pb(r);
        }
        int query;
        cin>>query;

        vector<string> queryvec;
        for(int i=0; i<=query-1; i++){
            string val;
            cin>>val;
            //trace(val);
            tuple<int,int,string> t = parsequery(val,r);
            cout<<get<0>(tuple);

        }
        cout<<endl;
    //}
    

}


//                                      ,
//                                ,   ,'|
//                              ,/|.-'   \.
//                           .-'  '       |.
//                     ,  .-'              |
//                    /|,'                 |'
//                   / '                    |  ,
//                  /                       ,'/
//               .  |          _              /
//                \`' .-.    ,' `.           |
//                 \ /   \ /      \          /
//                  \|    V        |        |  ,
//                   (           ) /.--.   ''"/
//                   "b.`. ,' _.ee'' 6)|   ,-'
//                     \"= --""  )   ' /.-'
//                      \ / `---"   ."|'
//                       \"..-    .'  |.
//                        `-__..-','   |
//                      __.) ' .-'/    /\._
//                _.--'/----..--------. _.-""-._
//             .-'_)   \.   /     __..-'     _.-'--.
//            / -'/      """""""""         ,'-.   . `.
//           | ' /                        /    `   `. \
//           |   |                        |         | |
//            \ .'\     CoderUnknown      |     \     |
//           / '  | ,'               . -  \`.    |  / /
//          / /   | |                      `/"--. -' /\
//         | |     \ \                     /     \     |
//  _Seal_ | \      | \                  .-|      |    |
