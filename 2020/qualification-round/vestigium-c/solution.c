#include <stdio.h>

void test_case(int t);

int main()
{
    int T;
    scanf("%d", &T);
    for (int t = 0; t < T; t++)
        test_case(t);
    return 0;
}

#define bool char
#define true 1
#define false 0

void test_case(int t)
{
    int N;
    scanf("%d", &N);
    int M[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            scanf("%d", &M[i][j]);

    int k = 0;
    for (int i = 0; i < N; i++)
        k += M[i][i];

    int r = 0;
    for (int i = 0; i < N; i++)
    {
        int *Mi = M[i];
        bool taken[N];
        for (int k = 0; k < N; k++)
            taken[k] = false;

        bool repeated = false;
        for (int j = 0; j < N; j++)
            if (taken[Mi[j] - 1])
            {
                repeated = true;
                break;
            }
            else
                taken[Mi[j] - 1] = true;

        if (repeated)
            r++;
    }

    int c = 0;
    for (int j = 0; j < N; j++)
    {
        bool taken[N];
        for (int k = 0; k < N; k++)
            taken[k] = false;

        bool repeated = false;
        for (int i = 0; i < N; i++)
            if (taken[M[i][j] - 1])
            {
                repeated = true;
                break;
            }
            else
                taken[M[i][j] - 1] = true;

        if (repeated)
            c++;
    }

    printf("Case #%d: %d %d %d\n", t + 1, k, r, c);
}