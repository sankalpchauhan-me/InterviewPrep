// ██████╗ ██████╗ ██████╗ ███████╗██████╗ ██╗   ██╗███╗   ██╗██╗  ██╗███╗   ██╗ ██████╗ ██╗    ██╗███╗   ██╗
// ██╔════╝██╔═══██╗██╔══██╗██╔════╝██╔══██╗██║   ██║████╗  ██║██║ ██╔╝████╗  ██║██╔═══██╗██║    ██║████╗  ██║
// ██║     ██║   ██║██║  ██║█████╗  ██████╔╝██║   ██║██╔██╗ ██║█████╔╝ ██╔██╗ ██║██║   ██║██║ █╗ ██║██╔██╗ ██║
// ██║     ██║   ██║██║  ██║██╔══╝  ██╔══██╗██║   ██║██║╚██╗██║██╔═██╗ ██║╚██╗██║██║   ██║██║███╗██║██║╚██╗██║
// ╚██████╗╚██████╔╝██████╔╝███████╗██║  ██║╚██████╔╝██║ ╚████║██║  ██╗██║ ╚████║╚██████╔╝╚███╔███╔╝██║ ╚████║
//  ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝  ╚══╝╚══╝ ╚═╝  ╚═══╝
#include<bits/stdc++.h>
using namespace std;
#define ll long long

ll kadane(ll arr[], ll n){
    ll sum=0;
    ll ans= INT_MIN;
    for(ll i=0; i<=n-1; i++){
        if(sum+arr[i]>0){
            sum = sum+arr[i];
        }
        else{
            sum=0;
        }
        ans = max(sum, ans);
    }

    return ans;
}

ll max_sum_subarray(ll a[], ll n, ll k){
    ll kadane_sum = kadane(a,n);
    if(k==1){
        return kadane_sum;
    }
    ll curr_prefix_sum = 0, curr_suffix_sum = 0; 
    ll max_prefix_sum = INT_MIN, max_suffix_sum = INT_MIN;

    for (int i = 0; i <n ; i++){
		curr_prefix_sum += a[i];
		max_prefix_sum = max(curr_prefix_sum, max_prefix_sum);
	}

    ll total_sum = curr_prefix_sum;

	for (int i = n-1; i >= 0; i--){
		curr_suffix_sum += a[i];
		max_suffix_sum = max(max_suffix_sum, curr_suffix_sum);
	}
	ll ans;

    if(total_sum<0){
        ans = max(max_suffix_sum+max_prefix_sum, kadane_sum);
    }
    else{
        ans = max(max_prefix_sum+max_suffix_sum+(k-2)*total_sum, kadane_sum);
    }

    return ans;

}
int main(){
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    #ifndef ONLINE_JUDGE
    freopen("input.txt","r",stdin);
    freopen("output.txt","w",stdout);
    #endif
    // your code goes here
	ll t;
	cin>>t;
	while(t--){
	    ll arr[10000005];
	    ll N,K;
	    ll var=0;
	    cin>>N>>K;
	    for(int i=0; i<=N-1; i++){
	        cin>>arr[i];
	    }
        cout<<max_sum_subarray(arr, N, K)<<endl;
	    
	   
	}
	
	return 0;

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