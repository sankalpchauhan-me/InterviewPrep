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


int main()
{
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    #ifndef ONLINE_JUDGE
    freopen("./../input.txt","r",stdin);
    freopen("./../output.txt","w",stdout);
    #endif

    testcases{
        int n,m;
        cin>>n>>m;
        vector<int> vec;
        for(int i=0; i<=n-1; i++){
            int val;
            cin>>val;
            vec.push_back(val);
        }

        sort(vec.begin(), vec.end());
        //1 3 4 7 9 9 12 56
        int mindiff = INT_MAX;
        for(int i=0; i<=n-1; i++){
            for(int j=i; j<=m-1; j++){
                trace(mindiff, i, j, vec[j]);
                if(vec[m-1]-vec[i]<mindiff){
                    mindiff = vec[m-1]-vec[i];
                }
            }
        }

        cout<<mindiff<<endl;
        
        
    }
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
