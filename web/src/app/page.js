import Image from "next/image";
import Link from 'next/link';
import SignUpPage from "@/app/pages/signup";
export default function Home() {
    return (
        <main className="flex min-h-screen flex-col items-center justify-center p-24">
            <div className="relative flex place-items-center">
                <Image
                    src="/next.svg"
                    alt="Next.js Logo"
                    width={180}
                    height={37}
                    priority
                />
            </div>
            <div>
                <SignUpPage />

            </div>
        </main>

    );
}
